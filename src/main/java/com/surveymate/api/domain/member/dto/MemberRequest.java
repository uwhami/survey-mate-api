package com.surveymate.api.domain.member.dto;

import com.surveymate.api.common.dto.MemInfoAware;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberRequest extends MemInfoAware {

    private String userId;
    private String userName;
    private String userEmail;
    private MultipartFile profileImageUuid;

}
