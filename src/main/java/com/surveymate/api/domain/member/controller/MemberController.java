package com.surveymate.api.domain.member.controller;


import com.surveymate.api.domain.member.dto.ChangePasswordRequestDTO;
import com.surveymate.api.domain.member.dto.MemberRequestDTO;
import com.surveymate.api.domain.member.dto.MemberResponseDTO;
import com.surveymate.api.domain.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
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
    public MemberResponseDTO modify(HttpServletRequest request, MemberRequestDTO memberRequestDTO) throws Exception{
        String memNum = (String) request.getAttribute("memnum");
        memberRequestDTO.setMemNum(memNum);
        return memberService.modify(memberRequestDTO);
    }

    @PatchMapping("/change-password")
    public ResponseEntity<?> changePassword(HttpServletRequest request, @RequestBody ChangePasswordRequestDTO changePasswordRequestDTO) {
        try {
            String memNum = (String) request.getAttribute("memnum");
            changePasswordRequestDTO.setMemNum(memNum);
            memberService.changePasswordError(changePasswordRequestDTO);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
    }

}
