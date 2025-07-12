package com.surveymate.api.domain.group.service;

import com.surveymate.api.common.dto.PagedResponse;
import com.surveymate.api.domain.group.dto.GroupReponse;
import com.surveymate.api.domain.group.dto.GroupRequest;
import com.surveymate.api.domain.group.entity.Group;
import com.surveymate.api.domain.member.dto.MemberResponse;
import org.springframework.data.domain.Pageable;


public interface GroupService {

    Group createGroup(String groupName, String groupAuthCode, String userId);

    GroupReponse joinGroup(GroupRequest groupRequest);

    GroupReponse createGroupFromRequest(GroupRequest groupRequest) throws Exception;

    boolean isExistsGroupCode(String groupCode);

    void setGroupAuthCode(String groupCode, String authCode);

    Group validateGroupCodeAndGroupAuthCode(String groupCode, String groupAuthCode);

    GroupReponse selectGroupByUserId(long groupId);

    GroupReponse updateGroup(GroupRequest request);

    PagedResponse<MemberResponse> getActiveMembersByGroupId(Long groupId, Pageable pageable);

}
