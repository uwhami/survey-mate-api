package com.surveymate.api.domain.auth.mapper;

import com.surveymate.api.common.enums.MemberRole;
import com.surveymate.api.domain.auth.dto.RegisterRequest;
import com.surveymate.api.domain.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface AuthMemberMapper {

    @Mappings({
            @Mapping(target = "memNum", ignore = true),
            @Mapping(target = "passwordError", ignore = true),
            @Mapping(target = "profileImageUuid", ignore = true),
            @Mapping(target = "memStatus", ignore = true),
            @Mapping(target = "socialType", ignore = true),
            @Mapping(target = "createDate", ignore = true),
            @Mapping(target = "updateDate", ignore = true),
            @Mapping(target = "deactivatedDate", ignore = true),
            @Mapping(target = "memRole", expression = "java(toMemberRole(registerRequest.getMemRole()))")
    })
    Member toEntity(RegisterRequest registerRequest);

    default MemberRole toMemberRole(String memRole) {
        try {
            int code = Integer.parseInt(memRole); // String 값을 Integer로 변환
            return MemberRole.fromCode(code);    // Enum으로 변환
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid memRole value: " + memRole, e);
        }
    }

}
