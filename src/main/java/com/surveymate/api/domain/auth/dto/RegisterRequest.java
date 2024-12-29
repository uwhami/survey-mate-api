package com.surveymate.api.domain.auth.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {

    private String userId;
    private String password;
    private String userName;
    private String userEmail;
    private MultipartFile profileImage;
    private String joinDate;
    private String memRole;

}
