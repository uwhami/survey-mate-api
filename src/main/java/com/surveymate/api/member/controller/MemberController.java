package com.surveymate.api.member.controller;


import com.surveymate.api.member.dto.MemberLoginDTO;
import com.surveymate.api.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/login")
    public ResponseEntity<?> memberLogin(MemberLoginDTO memberLoginDTO) {
        try {
            Map<String, String> jwtResponse = memberService.loginMember(memberLoginDTO);
            return ResponseEntity.ok(jwtResponse);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
    }

}
