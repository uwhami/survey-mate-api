package com.surveymate.api.group;

import com.surveymate.api.common.dto.PagedResponse;
import com.surveymate.api.domain.group.dto.GroupReponse;
import com.surveymate.api.domain.group.service.GroupService;
import com.surveymate.api.domain.member.dto.MemberResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@SpringBootTest
public class GroupServiceTests {

    @Autowired
    GroupService groupService;

    @Test
    public void selectGroupInfoByUSer() throws Exception {
        GroupReponse groupReponse = groupService.selectGroupByUserId(2);
        System.out.println("selectGroupInfoByUSer===========" + groupReponse);
    }

    @Test
    void getActiveMembersByGroupId() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        PagedResponse<MemberResponse> response = groupService.getActiveMembersByGroupId(2L, pageable);
        System.out.println(response.toString());
    }
}
