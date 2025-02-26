package com.surveymate.api.group;

import com.surveymate.api.domain.group.dto.GroupReponse;
import com.surveymate.api.domain.group.dto.GroupRequest;
import com.surveymate.api.domain.group.service.GroupService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GroupServiceTests {

    @Autowired
    GroupService groupService;

    @Test
    public void selectGroupInfoByUSer() throws Exception {
        GroupRequest request = new GroupRequest();
        request.setMemNum("M202502020003");
        GroupReponse groupReponse = groupService.selectGroupByUserId(request);
        System.out.println("selectGroupInfoByUSer===========" + groupReponse);
    }

}
