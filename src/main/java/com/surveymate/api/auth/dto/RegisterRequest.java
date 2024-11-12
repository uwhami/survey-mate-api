package com.surveymate.api.auth.dto;

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
    private MultipartFile profileImage;
    private String joinDate;
    private String memStatus;

}
