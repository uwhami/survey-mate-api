package com.surveymate.api.domain.member.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MemberResponseDTO {

    private String memNum;
    private String userId;
    private String userName;
    private String profileImageUri;
    private String joinDate;
    private String memStatus;
}