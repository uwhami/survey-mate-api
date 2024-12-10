package com.surveymate.api.domain.member.service;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import com.surveymate.api.common.enums.FilePath;
import com.surveymate.api.common.exception.CustomRuntimeException;
import com.surveymate.api.domain.member.dto.ChangePasswordRequestDTO;
import com.surveymate.api.domain.member.dto.MemberRequestDTO;
import com.surveymate.api.domain.member.dto.MemberResponseDTO;
import com.surveymate.api.domain.member.entity.Member;
import com.surveymate.api.domain.member.entity.QMember;
import com.surveymate.api.domain.member.exception.PasswordMismatchException;
import com.surveymate.api.domain.member.exception.UserNotFoundException;
import com.surveymate.api.domain.member.mapper.MemberMapper;
import com.surveymate.api.domain.member.repository.MemberRepository;
import com.surveymate.api.file.entity.UploadedFile;
import com.surveymate.api.file.service.FileService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
    private final FileService fileService;
    private final PasswordEncoder passwordEncoder;

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
    public MemberResponseDTO modify(MemberRequestDTO memberRequestDTO) throws Exception {

        Optional<Member> optionalMember = memberRepository.findByMemNum(memberRequestDTO.getMemNum());
        if (optionalMember.isEmpty()) {
            throw new UserNotFoundException();
        }


        Member existingMember = optionalMember.get();

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
                        }else if(value instanceof MultipartFile){
                            MultipartFile file = (MultipartFile) value;
                            if(!file.getName().isEmpty()){
                                String fileId = existingMember.getProfileImageUuid();
                                UploadedFile newFile = fileService.deleteAndSaveFile(fileId, file, FilePath.MEMBER_PROFILE);
                                value = newFile.getFileId();
                                existingMember.setProfileImageUuid(newFile.getFileId());
                            }else{
                                return;
                            }
                        }

                        // QueryDSL Path 생성 및 필드 업데이트
                        Path<Object> path = Expressions.path(Object.class, qMember, field.getName());
                        updateClause.set(path, value);
                    }
                } catch (IllegalAccessException e) {
                    throw new CustomRuntimeException("Reflection 필드 접근 중 에러 발생", e);
                } catch (Exception e) {
                    throw new CustomRuntimeException("정보 수정 중 에러 발생", e);
                }
            });

            updateClause.execute();
            entityManager.clear(); // 영속성 컨텍스트 비우기

        } catch (RuntimeException e) {
            throw new CustomRuntimeException("회원정보 수정 중 에러 발생.", e);
        }

        Member updatedMember = memberRepository.findByMemNum(memberRequestDTO.getMemNum())
                .orElseThrow(() -> new CustomRuntimeException("업데이트 후 사용자 정보를 조회할 수 없습니다."));

        MemberResponseDTO responseDTO = memberMapper.toDTO(updatedMember);
        if(existingMember.getProfileImageUuid() != null){
            responseDTO.setProfileImageUri(fileService.getFilePath(existingMember.getProfileImageUuid()));
        }
        return responseDTO;
    }

    @Transactional
    @Override
    public void changePasswordError(ChangePasswordRequestDTO changePasswordRequestDTO) {

        QMember qMember = QMember.member;

        // 사용자 조회
        Member member = memberRepository.findByMemNum(changePasswordRequestDTO.getMemNum())
                .orElseThrow(() -> new UserNotFoundException());

        // 기존 비밀번호 검증
        if (!passwordEncoder.matches(changePasswordRequestDTO.getOldPassword(), member.getPassword())) {
            throw new PasswordMismatchException();
        }

        String encodedNewPassword = passwordEncoder.encode(changePasswordRequestDTO.getNewPassword());
        member.setPassword(encodedNewPassword);
        memberRepository.save(member);

    }
}
