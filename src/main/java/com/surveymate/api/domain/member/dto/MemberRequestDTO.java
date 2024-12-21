package com.surveymate.api.domain.member.dto;

import com.surveymate.api.common.dto.MemnumAware;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberRequestDTO implements MemnumAware {

    private String memNum;
    private String userId;
    private String userName;
    private String userEmail;
    private MultipartFile profileImageUuid;

    @Override
    public void setMemnum(String memNum) {
        this.memNum = memNum;
    }
}
