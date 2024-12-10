package com.surveymate.api.domain.member.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MemberRequestDTO {

    private String memNum;
    private String userId;
    private String userName;
    private String userEmail;
    private MultipartFile profileImageUuid;

}
