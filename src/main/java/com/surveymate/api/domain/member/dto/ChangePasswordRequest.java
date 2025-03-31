package com.surveymate.api.domain.member.dto;


import com.surveymate.api.common.dto.MemInfoAware;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ChangePasswordRequest extends MemInfoAware {

    private String oldPassword;  // 기존 비밀번호
    private String newPassword;  // 새 비밀번호

}
