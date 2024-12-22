package com.surveymate.api.domain.member.controller;


import com.surveymate.api.domain.member.dto.ChangePasswordRequest;
import com.surveymate.api.domain.member.dto.MemberRequest;
import com.surveymate.api.domain.member.dto.MemberResponse;
import com.surveymate.api.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;


    @PatchMapping("/modify")
    public MemberResponse modify(MemberRequest memberRequest) throws Exception{
        return memberService.modify(memberRequest);
    }

    @PatchMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        try {
            memberService.changePasswordError(changePasswordRequest);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
    }

}
