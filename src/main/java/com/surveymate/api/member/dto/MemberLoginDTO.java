package com.surveymate.api.member.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberLoginDTO {

    private String userId;
    private String password;

}
