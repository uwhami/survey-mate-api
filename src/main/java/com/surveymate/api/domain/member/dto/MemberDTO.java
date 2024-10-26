package com.surveymate.api.domain.member.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDTO {

    private String memNum;
    private String userId;
    private String userName;
    private String profileImage;
    private String joinDate;
    private String memStatus;
}
