package com.surveymate.api.member.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberLoginDTO {

    private String memberId;
    private String password;

}
