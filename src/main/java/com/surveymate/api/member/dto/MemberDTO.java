package com.surveymate.api.member.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDTO {

    private String memNum;
    private String userId;
    private String userName;
    private String profileImageUri;
    private String joinDate;
    private String memStatus;
}
