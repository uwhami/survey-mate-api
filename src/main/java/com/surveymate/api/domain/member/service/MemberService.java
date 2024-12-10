package com.surveymate.api.domain.member.service;


import com.surveymate.api.domain.member.dto.ChangePasswordRequestDTO;
import com.surveymate.api.domain.member.dto.MemberRequestDTO;
import com.surveymate.api.domain.member.dto.MemberResponseDTO;

public interface MemberService {

    boolean checkDuplicateId(String userId);

    boolean existsByUserId(String userId);

    void increasePasswordError(String userId);

    void resetPasswordError(String userId);

    int findPasswordErrorByUserId(String userId);

    MemberResponseDTO modify(MemberRequestDTO memberRequestDTO) throws Exception;

    void changePasswordError(ChangePasswordRequestDTO changePasswordRequestDTO) throws Exception;

}
