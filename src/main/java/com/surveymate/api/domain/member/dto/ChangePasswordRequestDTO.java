package com.surveymate.api.domain.member.dto;


import com.surveymate.api.common.dto.MemnumAware;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequestDTO implements MemnumAware {

    private String memNum;
    private String oldPassword;  // 기존 비밀번호
    private String newPassword;  // 새 비밀번호

    @Override
    public void setMemnum(String memnum) {
        this.memNum = memnum;
    }
}
