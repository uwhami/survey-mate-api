package com.surveymate.api.member;


import com.surveymate.api.member.service.MemberService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@Log4j2
@SpringBootTest
public class MemberServiceTests {

    @Autowired
    private MemberService memberService;



}
