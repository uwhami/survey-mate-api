package com.surveymate.api.member;


import com.surveymate.api.domain.member.dto.MemberDTO;
import com.surveymate.api.domain.member.entity.Member;
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
    void testCreateMember() throws Exception {
        MemberDTO memberDTO = MemberDTO.builder()
                .memNum("")
                .userId("user3")
                .password("12341234")
                .userName("John Doe")
                .profileImage("profile.jpg")
                .joinDate("20231022")
                .memStatus("1")
                .build();
        Member member = memberService.createMember(memberDTO);

        System.out.println(member.toString());


    }

}
