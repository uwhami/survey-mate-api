package com.surveymate.api.domain.member.dto;


import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChangePasswordRequestDTO {
    private String memNum;       // 사용자 번호
    private String oldPassword;  // 기존 비밀번호
    private String newPassword;  // 새 비밀번호

    public void setMemNum(String memNum) {
        this.memNum = memNum;
    }
}
