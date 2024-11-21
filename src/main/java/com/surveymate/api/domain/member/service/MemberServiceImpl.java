package com.surveymate.api.domain.member.service;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import com.surveymate.api.common.exception.CustomRuntimeException;
import com.surveymate.api.domain.member.dto.MemberRequestDTO;
import com.surveymate.api.domain.member.dto.MemberResponseDTO;
import com.surveymate.api.domain.member.entity.Member;
import com.surveymate.api.domain.member.entity.QMember;
import com.surveymate.api.domain.member.exception.UserNotFoundException;
import com.surveymate.api.domain.member.mapper.MemberMapper;
import com.surveymate.api.domain.member.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    @PersistenceContext
    private EntityManager entityManager;

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    @Override
    public boolean checkDuplicateId(String userId) {
        return existsByUserId(userId);
    }

    @Override
    public boolean existsByUserId(String userId) {
        Optional<Member> existId = memberRepository.findByUserId(userId);
        return existId.isPresent();
    }

    @Transactional
    @Override
    public void increasePasswordError(String userId) {
        memberRepository.increasePasswordError(userId);
    }

    @Transactional
    @Override
    public void resetPasswordError(String userId) {
        memberRepository.resetPasswordError(userId);
    }

    @Override
    public int findPasswordErrorByUserId(String userId) {
        return memberRepository.findPasswordErrorByUserId(userId);
    }


    @Transactional
    @Override
    public MemberResponseDTO modify(MemberRequestDTO memberRequestDTO) throws Exception{

        Optional<Member> optionalMember = memberRepository.findByMemNum(memberRequestDTO.getMemNum());
        if (optionalMember.isEmpty()) {
            throw new UserNotFoundException("해당 사용자가 존재하지 않습니다.");
        }

        try {

            // QueryDSL을 사용하여 동적으로 필드 업데이트
            QMember qMember = QMember.member;

            JPAUpdateClause updateClause = new JPAQueryFactory(entityManager)
                    .update(qMember)
                    .where(qMember.memNum.eq(memberRequestDTO.getMemNum()));

            Arrays.stream(MemberRequestDTO.class.getDeclaredFields()).forEach(field -> {
                try {
                    field.setAccessible(true); // private 필드 접근 가능하게 설정

                    if ("memNum".equals(field.getName())) {
                        return; // 다음 필드로 넘어감
                    }

                    Object value = field.get(memberRequestDTO); // 필드 값 가져오기
                    if (value != null) {
                        if (value instanceof String) {
                            String stringValue = ((String) value).trim();
                            if (stringValue.isEmpty()) {
                                return;
                            }
                        }else{
                            // 파일인 경우 추가 예정.


//                            return;
                        }

                        // QueryDSL Path 생성 및 필드 업데이트
                        Path<Object> path = Expressions.path(Object.class, qMember, field.getName());
                        updateClause.set(path, value);
                    }
                } catch (IllegalAccessException e) {
                    throw new CustomRuntimeException("Reflection 필드 접근 중 에러 발생", e);
                }
            });

            updateClause.execute();
            entityManager.clear();

        } catch (RuntimeException e) {
            throw new CustomRuntimeException("회원정보 수정 중 에러 발생.", e);
        }

        Member updatedMember = memberRepository.findByMemNum(memberRequestDTO.getMemNum())
                .orElseThrow(() -> new CustomRuntimeException("업데이트 후 사용자 정보를 조회할 수 없습니다."));

        return memberMapper.toDTO(updatedMember);
    }
}
