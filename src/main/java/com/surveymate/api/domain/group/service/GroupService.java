package com.surveymate.api.domain.group.service;

import com.surveymate.api.domain.group.dto.GroupReponse;
import com.surveymate.api.domain.group.dto.GroupRequest;
import com.surveymate.api.domain.group.entity.Group;


public interface GroupService {

    Group createGroup(String groupName, String groupAuthCode, String userId);

    boolean isExistsGroupCode(String groupCode);

    void setGroupAuthCode(String groupCode, String authCode);

    Group validateGroupCodeAndGroupAuthCode(String groupCode, String groupAuthCode);

    GroupReponse selectGroupByUserId(GroupRequest request);
}
