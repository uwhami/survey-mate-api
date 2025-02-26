package com.surveymate.api.domain.group.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class GroupReponse {

    private final String groupCode;     // 초대 코드 (유니크)
    private final String groupName;     // 그룹 이름
    private final String groupAuthCode; // 그룹 인증번호

}
