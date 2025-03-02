package com.surveymate.api.domain.group.controller;

import com.surveymate.api.domain.group.dto.GroupReponse;
import com.surveymate.api.domain.group.dto.GroupRequest;
import com.surveymate.api.domain.group.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/group")
@RestController
public class GroupController {

    private final GroupService groupService;

    @GetMapping("/{groupId}")
    public GroupReponse getGroupInfo(@PathVariable Long groupId) {
        return groupService.selectGroupByUserId(groupId);
    }

    @PutMapping()
    public GroupReponse updateGroup(@RequestBody GroupRequest request) throws Exception{
        if(request.getGroupId() == null) {
            return groupService.createGroupFromRequest(request);
        }else{
            return groupService.updateGroup(request);
        }
    }



}
