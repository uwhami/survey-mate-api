package com.surveymate.api.domain.member.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MemberResponse {

    private String userId;
    private String userName;
    private String userEmail;
    private String profileImageUri;
    private String joinDate;
    private String memStatus;
    private String memRole;

}
