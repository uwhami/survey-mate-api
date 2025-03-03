package com.surveymate.api.member;


import com.surveymate.api.common.dto.PagedResponse;
import com.surveymate.api.domain.member.dto.ChangePasswordRequest;
import com.surveymate.api.domain.member.dto.MemberRequest;
import com.surveymate.api.domain.member.dto.MemberResponse;
import com.surveymate.api.domain.member.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
public class MemberServiceTests {

    @Autowired
    private MemberService memberService;

    @Rollback(false)
    @Transactional
    @Test
    void modify() throws Exception {
        MemberRequest memberRequest = MemberRequest.builder()
                .userId(null)
                .userName("modifyTest2")
                .profileImageUuid(null)
                .build();
        memberRequest.setMemNum("M202411200001");

        try{
            MemberResponse responseDTO = memberService.modify(memberRequest);
            System.out.println(responseDTO.toString());
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    @Test
    void changePassword() throws Exception {
        ChangePasswordRequest changePasswordRequest = ChangePasswordRequest.builder()
                .oldPassword("1111")
                .newPassword("1111")
                .build();
        changePasswordRequest.setMemNum("M202412100001");
        memberService.changePasswordError(changePasswordRequest);
    }

    @Test
    void getActiveMembersByGroupId() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        PagedResponse<MemberResponse> response = memberService.getActiveMembersByGroupId(2L, pageable);
        System.out.println(response.toString());
    }

}
