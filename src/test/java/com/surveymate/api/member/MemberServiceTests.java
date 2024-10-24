package com.surveymate.api.member;


import com.surveymate.api.domain.member.dto.MemberDTO;
import com.surveymate.api.domain.member.dto.MemberSignupDTO;
import com.surveymate.api.domain.member.service.MemberService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@Log4j2
@SpringBootTest
public class MemberServiceTests {

    @Autowired
    private MemberService memberService;

    @Test
    void testCreateMember() {
        MemberSignupDTO memberSignupDTO = MemberSignupDTO.builder()
                .userId("user11")
                .password("password")
                .userName("John Doe")
                .profileImage("profile.jpg")
                .joinDate("20241024")
                .memStatus("1")
                .build();
        MemberDTO member = memberService.createMember(memberSignupDTO);

        System.out.println(member.toString());


    }

}
