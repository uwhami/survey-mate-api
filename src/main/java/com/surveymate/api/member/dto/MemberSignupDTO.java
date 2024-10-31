package com.surveymate.api.member.dto;


import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberSignupDTO {

    private String userId;
    private String password;
    private String userName;
    private MultipartFile profileImage;
    private String joinDate;
    private String memStatus;

}
