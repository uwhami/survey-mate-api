package com.surveymate.api.domain.member.service;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import com.surveymate.api.common.enums.FilePath;
import com.surveymate.api.common.enums.MemberRole;
import com.surveymate.api.common.enums.MemberStatus;
import com.surveymate.api.common.exception.CustomRuntimeException;
import com.surveymate.api.domain.auth.dto.PasswordResetRequest;
import com.surveymate.api.domain.group.entity.Group;
import com.surveymate.api.domain.member.dto.ChangePasswordRequest;
import com.surveymate.api.domain.member.dto.MemberRequest;
import com.surveymate.api.domain.member.dto.MemberResponse;
import com.surveymate.api.domain.member.entity.Member;
import com.surveymate.api.domain.member.entity.QMember;
import com.surveymate.api.domain.member.exception.PasswordMismatchException;
import com.surveymate.api.domain.member.exception.UserNotFoundException;
import com.surveymate.api.domain.member.mapper.MemberMapper;
import com.surveymate.api.domain.member.repository.MemberRepository;
import com.surveymate.api.file.entity.UploadedFile;
import com.surveymate.api.file.service.FileService;
import jakarta.persistence.EntityManager;
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

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;
    private final FileService fileService;
    private final PasswordEncoder passwordEncoder;
    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager entityManager;

    @Override
    public boolean checkDuplicateId(String userId) {
        return existsByUserId(userId);
    }

    @Override
    public boolean existsByUserId(String userId) {
        Optional<Member> existId = memberRepository.findByUserId(userId);
        return existId.isPresent();
    }

    @Override
    public boolean checkDuplicatedEmail(String email) {
        try {
            Optional<Member> existEmail = memberRepository.findByUserEmail(email);
            return existEmail.isPresent();
        } catch (Exception e) {
            throw new CustomRuntimeException("이메일 확인 도중 에러가 발생했습니다.");
        }

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

    @Override
    public MemberResponse getMemInfo(MemberRequest request) throws Exception {
        Optional<Member> optionalMember = memberRepository.findByMemNum(request.getMemNum());
        if(optionalMember.isEmpty()) {
            throw new UserNotFoundException();
        }

        /* 프로필 이미지 경로 따로 넣어주기. */
        Member member = optionalMember.get();
        MemberResponse memberResponse = memberMapper.toDTO(member);
        memberResponse.setProfileImageUri(fileService.getFilePath(member.getProfileImageUuid()));
        if(member.getGroup() != null) {
            memberResponse.setGroupName(member.getGroup().getGroupName());
            memberResponse.setGroupId(member.getGroup().getGroupId());
        }
        return memberResponse;
    }

    @Transactional
    @Override
    public MemberResponse modify(MemberRequest memberRequest) throws Exception {

        Optional<Member> optionalMember = memberRepository.findByMemNum(memberRequest.getMemNum());
        if (optionalMember.isEmpty()) {
            throw new UserNotFoundException();
        }


        Member existingMember = optionalMember.get();

        try {
            // QueryDSL을 사용하여 동적으로 필드 업데이트
            QMember qMember = QMember.member;

            JPAUpdateClause updateClause = jpaQueryFactory
                    .update(qMember)
                    .where(qMember.memNum.eq(memberRequest.getMemNum()));

            Arrays.stream(MemberRequest.class.getDeclaredFields()).forEach(field -> {
                try {
                    field.setAccessible(true); // private 필드 접근 가능하게 설정

                    if ("memNum".equals(field.getName())) {
                        return; // 다음 필드로 넘어감
                    }

                    Object value = field.get(memberRequest); // 필드 값 가져오기
                    if (value != null) {
                        if (value instanceof String) {
                            String stringValue = ((String) value).trim();
                            if (stringValue.isEmpty()) {
                                return;
                            }
                        } else if (value instanceof MultipartFile file) {
                            if (!file.getName().isEmpty()) {
                                String fileId = existingMember.getProfileImageUuid();
                                UploadedFile newFile = fileService.deleteAndSaveFile(fileId, file, FilePath.MEMBER_PROFILE);
                                value = newFile.getFileId();
                                existingMember.setProfileImageUuid(newFile.getFileId());
                            } else {
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

        } catch (RuntimeException e) {
            throw new CustomRuntimeException("회원정보 수정 중 에러 발생.", e);
        }

        entityManager.clear();


        Member updatedMember = memberRepository.findByMemNum(memberRequest.getMemNum())
                .orElseThrow(() -> new CustomRuntimeException("업데이트 후 사용자 정보를 조회할 수 없습니다."));

        MemberResponse responseDTO = memberMapper.toDTO(updatedMember);
        if (existingMember.getProfileImageUuid() != null) {
            responseDTO.setProfileImageUri(fileService.getFilePath(existingMember.getProfileImageUuid()));
        }
        return responseDTO;
    }

    @Override
    public void assignGroupToMember(String memnum, Group group) {
        Member member = memberRepository.findByMemNum(memnum).orElseThrow(UserNotFoundException::new);
        member.setGroup(group);
        memberRepository.save(member);
    }

    @Transactional
    @Override
    public void changePasswordError(ChangePasswordRequest changePasswordRequest) {

        QMember qMember = QMember.member;

        Member member = memberRepository.findByMemNum(changePasswordRequest.getMemNum())
                .orElseThrow(() -> new UserNotFoundException());

        // 기존 비밀번호 검증
        if (!passwordEncoder.matches(changePasswordRequest.getOldPassword(), member.getPassword())) {
            throw new PasswordMismatchException();
        }

        String encodedNewPassword = passwordEncoder.encode(changePasswordRequest.getNewPassword());

        try {
            jpaQueryFactory.update(qMember)
                    .set(qMember.password, encodedNewPassword)
                    .where(qMember.memNum.eq(changePasswordRequest.getMemNum()))
                    .execute();
        } catch (Exception e) {
            throw new CustomRuntimeException("비밀번호 변경을 실패했습니다.", e);
        }


    }

    @Transactional
    @Override
    public void passwordReset(PasswordResetRequest request) {
        QMember qMember = QMember.member;

        try {
            // 사용자 조회
            Member member = memberRepository.findByUserEmail(request.getUserEmail(), MemberStatus.ACTIVE)
                    .orElseThrow(() -> new UserNotFoundException());

            String encodedNewPassword = passwordEncoder.encode(request.getNewPassword());

            jpaQueryFactory.update(qMember)
                    .set(qMember.password, encodedNewPassword)
                    .set(qMember.passwordError, 0)
                    .where(qMember.memNum.eq(member.getMemNum()))
                    .execute();
        } catch (UserNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomRuntimeException("비밀번호 재설정을 실패했습니다.", e);
        }

    }

    @Transactional
    @Override
    public void deleteMember(MemberRequest request) {
        QMember qMember = QMember.member;
        try{
            Member member = memberRepository.findByMemNum(request.getMemNum()).get();
            jpaQueryFactory.update(qMember)
                    .set(qMember.memStatus, MemberStatus.DEACTIVATED)
                    .set(qMember.deactivatedDate, java.time.LocalDateTime.now())
                    .where(qMember.memNum.eq(member.getMemNum()))
                    .execute();
        }catch(Exception e){
            throw new CustomRuntimeException("회원탈퇴 중 에러 발생.", e);
        }
    }

    @Transactional
    @Override
    public void updateMemberRoleToUser(String memNum, MemberRole role) {
        try{
            int cnt = memberRepository.updateMemberRoleToUser(memNum, role);
            if(cnt == 0){
                throw new UserNotFoundException("Error Updating Member Role..");
            }
        }catch (Exception e){
            throw new CustomRuntimeException(e.getMessage(), e);
        }

    }
}
