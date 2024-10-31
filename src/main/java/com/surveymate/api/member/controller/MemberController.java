package com.surveymate.api.member.controller;


import com.surveymate.api.member.dto.MemberDTO;
import com.surveymate.api.member.dto.MemberSignupDTO;
import com.surveymate.api.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/check-duplicate-id")
    public Map<String, String> checkDuplicateId(@RequestParam String userId) {
        return memberService.checkDuplicateId(userId);
    }

    @PostMapping("/register")
    public MemberDTO createMember(MemberSignupDTO memberSignupDTO) throws Exception {
        return memberService.createMember(memberSignupDTO);
    }

}
