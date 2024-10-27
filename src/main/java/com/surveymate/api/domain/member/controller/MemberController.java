package com.surveymate.api.domain.member.controller;


import com.surveymate.api.domain.member.dto.MemberDTO;
import com.surveymate.api.domain.member.dto.MemberSignupDTO;
import com.surveymate.api.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public MemberDTO createMember(@RequestBody MemberSignupDTO memberSignupDTO) throws Exception {
        return memberService.createMember(memberSignupDTO);
    }

}
