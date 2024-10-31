package com.surveymate.api.member;

import com.surveymate.api.member.entity.Member;
import com.surveymate.api.member.repository.MemberRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Log4j2
@SpringBootTest
public class MemberRepositoryTests {

    @Autowired
    MemberRepository memberRepository;


    @Test
    public void save() {
        Member member = Member.builder()
                .memNum("")
                .userId("user123")
                .password("encodedPassword")
                .userName("John Doe")
                .profileImage("profile.jpg")
                .joinDate("20231022")
                .memStatus("A")
                .build();
        memberRepository.save(member);

        for(Member m : memberRepository.findAll()) {
            System.out.println("==========Member-----=========");
            System.out.println(m.toString());
        }
    }
}
