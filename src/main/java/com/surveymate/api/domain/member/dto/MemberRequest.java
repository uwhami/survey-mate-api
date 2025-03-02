package com.surveymate.api.domain.member.dto;

import com.surveymate.api.common.dto.MemnumAware;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberRequest extends MemnumAware {

    private String userId;
    private String userName;
    private String userEmail;
    private MultipartFile profileImageUuid;
    private long groupId;

}
