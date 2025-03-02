package com.surveymate.api.domain.member.service;


import com.surveymate.api.domain.auth.dto.PasswordResetRequest;
import com.surveymate.api.domain.group.entity.Group;
import com.surveymate.api.domain.member.dto.ChangePasswordRequest;
import com.surveymate.api.domain.member.dto.MemberRequest;
import com.surveymate.api.domain.member.dto.MemberResponse;

public interface MemberService {

    boolean checkDuplicateId(String userId);

    boolean checkDuplicatedEmail(String email);

    boolean existsByUserId(String userId);

    void increasePasswordError(String userId);

    void resetPasswordError(String userId);

    int findPasswordErrorByUserId(String userId);

    MemberResponse getMemInfo(MemberRequest request) throws Exception;

    MemberResponse modify(MemberRequest request) throws Exception;

    void assignGroupToMember(String memnum, Group group);

    void changePasswordError(ChangePasswordRequest request) throws Exception;

    void passwordReset(PasswordResetRequest request);

    void deleteMember(MemberRequest request);
}
