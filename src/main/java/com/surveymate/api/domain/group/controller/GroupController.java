package com.surveymate.api.domain.group.controller;

import com.surveymate.api.common.dto.PagedResponse;
import com.surveymate.api.domain.group.dto.GroupReponse;
import com.surveymate.api.domain.group.dto.GroupRequest;
import com.surveymate.api.domain.group.service.GroupService;
import com.surveymate.api.domain.member.dto.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PostMapping()
    public GroupReponse createGroup(@RequestBody GroupRequest request) throws Exception{
        return groupService.createGroupFromRequest(request);
    }

    @PostMapping("/join")
    public GroupReponse joinGroup(@RequestBody GroupRequest request) throws Exception{
        return groupService.joinGroup(request);
    }

    @PreAuthorize("hasAnyRole('MANAGER')")
    @PutMapping()
    public GroupReponse updateGroup(@RequestBody GroupRequest request) throws Exception{
        return groupService.updateGroup(request);
    }

    @GetMapping("/{groupId}/members")
    public PagedResponse<MemberResponse> getGroupMembers(
            @PathVariable Long groupId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return groupService.getActiveMembersByGroupId(groupId, pageable);
    }

}
