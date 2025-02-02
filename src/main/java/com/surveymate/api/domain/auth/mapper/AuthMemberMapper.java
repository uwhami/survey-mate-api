package com.surveymate.api.domain.auth.mapper;

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
            @Mapping(target = "group", ignore = true),
            @Mapping(target = "deactivatedDate", ignore = true),
            @Mapping(target = "memRole", ignore = true)
    })
    Member toEntity(RegisterRequest registerRequest);

}
