package com.surveymate.api.member;


import com.surveymate.api.domain.member.dto.ChangePasswordRequestDTO;
import com.surveymate.api.domain.member.dto.MemberRequestDTO;
import com.surveymate.api.domain.member.dto.MemberResponseDTO;
import com.surveymate.api.domain.member.entity.Member;
import com.surveymate.api.domain.member.service.MemberService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;


@Log4j2
@SpringBootTest
public class MemberServiceTests {

    @Autowired
    private MemberService memberService;

    @Rollback(false)
    @Transactional
    @Test
    void modify() throws Exception {
        MemberRequestDTO memberRequestDTO = MemberRequestDTO.builder()
                .memNum("M202411200001")
                .userId(null)
                .userName("modifyTest2")
                .profileImageUuid(null)
                .build();

        MemberResponseDTO responseDTO = memberService.modify(memberRequestDTO);
        System.out.println(responseDTO.toString());

    }

    @Test
    void changePassword() throws Exception {
        ChangePasswordRequestDTO changePasswordRequestDTO = ChangePasswordRequestDTO.builder()
                .memNum("M202412100001")
                .oldPassword("1234")
                .newPassword("1111")
                .build();

        memberService.changePasswordError(changePasswordRequestDTO);
    }


}
