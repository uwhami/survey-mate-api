package com.surveymate.api.member;


import com.surveymate.api.member.dto.MemberDTO;
import com.surveymate.api.member.dto.MemberSignupDTO;
import com.surveymate.api.member.service.MemberService;
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
    void testCreateMember() throws Exception {
        MemberSignupDTO memberSignupDTO = MemberSignupDTO.builder()
                .userId("user11")
                .password("password")
                .userName("John Doe")
                .joinDate("20241024")
                .memStatus("1")
                .build();
        MemberDTO member = memberService.createMember(memberSignupDTO);

        System.out.println(member.toString());


    }

}
