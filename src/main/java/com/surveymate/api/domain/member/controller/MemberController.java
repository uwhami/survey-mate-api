package com.surveymate.api.domain.member.controller;


import com.surveymate.api.domain.member.dto.ChangePasswordRequest;
import com.surveymate.api.domain.member.dto.MemberRequest;
import com.surveymate.api.domain.member.dto.MemberResponse;
import com.surveymate.api.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/getMemInfo")
    public MemberResponse getMemInfo(MemberRequest request) throws Exception{
        return memberService.getMemInfo(request);
    }

    @PatchMapping("/modify")
    public MemberResponse modify(MemberRequest memberRequest) throws Exception{
        return memberService.modify(memberRequest);
    }

    @PatchMapping("/change-password")
    public void changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) throws Exception{
        memberService.changePasswordError(changePasswordRequest);
    }

    @DeleteMapping
    public void deleteMember(MemberRequest memberRequest) {
        memberService.deleteMember(memberRequest);
    }
}
