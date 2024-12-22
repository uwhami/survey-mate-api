package com.surveymate.api.domain.member.service;


import com.surveymate.api.domain.auth.dto.PasswordResetRequest;
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

    MemberResponse modify(MemberRequest memberRequest) throws Exception;

    void changePasswordError(ChangePasswordRequest changePasswordRequest) throws Exception;

    void passwordReset(PasswordResetRequest request);

}
