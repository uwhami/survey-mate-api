package com.surveymate.api.domain.member.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberSignupDTO {

    private String userId;
    private String password;
    private String userName;
    private String profileImage;
    private String joinDate;
    private String memStatus;

}
