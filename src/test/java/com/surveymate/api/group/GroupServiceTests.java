package com.surveymate.api.group;

import com.surveymate.api.domain.group.dto.GroupReponse;
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
        GroupReponse groupReponse = groupService.selectGroupByUserId(2);
        System.out.println("selectGroupInfoByUSer===========" + groupReponse);
    }

}
