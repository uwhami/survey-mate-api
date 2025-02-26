package com.surveymate.api.domain.group.controller;

import com.surveymate.api.domain.group.dto.GroupReponse;
import com.surveymate.api.domain.group.dto.GroupRequest;
import com.surveymate.api.domain.group.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/group")
@RestController
public class GroupController {

    private final GroupService groupService;

    @GetMapping("/getGroupInfo")
    public GroupReponse getGroupInfo(GroupRequest groupRequest) {
        return groupService.selectGroupByUserId(groupRequest);
    }

}
