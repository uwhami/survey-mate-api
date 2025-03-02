package com.surveymate.api.domain.group.dto;

import com.surveymate.api.common.dto.MemnumAware;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class GroupRequest extends MemnumAware {

    private Long groupId;
    private String groupCode;     // 초대 코드 (유니크)
    private String groupName;     // 그룹 이름
    private String groupAuthCode; // 그룹 인증번호

}
