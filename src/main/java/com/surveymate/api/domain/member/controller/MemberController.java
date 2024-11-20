package com.surveymate.api.domain.member.controller;


import com.surveymate.api.domain.member.dto.MemberRequestDTO;
import com.surveymate.api.domain.member.dto.MemberResponseDTO;
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


    @GetMapping("/check-duplicate-id")
    public ResponseEntity<Void> checkDuplicateId(@RequestParam String userId) {
        boolean existId = memberService.checkDuplicateId(userId);
        if (existId) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/modify")
    public MemberResponseDTO modify(MemberRequestDTO memberRequestDTO) throws Exception{
        return memberService.modify(memberRequestDTO);
    }


}
