package com.surveymate.api.member.service;

import java.util.Map;


public interface MemberService {

    Map<String,String> checkDuplicateId(String userId);

    boolean existsByUserId(String userId);

    void increasePasswordError(String userId);

    void resetPasswordError(String userId);

}
