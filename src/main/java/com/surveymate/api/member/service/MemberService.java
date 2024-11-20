package com.surveymate.api.member.service;


public interface MemberService {

    boolean checkDuplicateId(String userId);

    boolean existsByUserId(String userId);

    void increasePasswordError(String userId);

    void resetPasswordError(String userId);

    int findPasswordErrorByUserId(String userId);

}
