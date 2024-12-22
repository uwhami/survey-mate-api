package com.surveymate.api.domain.member.dto;


import com.surveymate.api.common.dto.MemnumAware;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
public class ChangePasswordRequest extends MemnumAware {

    private String oldPassword;  // 기존 비밀번호
    private String newPassword;  // 새 비밀번호

}
