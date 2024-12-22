package com.surveymate.api.domain.auth.mapper;

import com.surveymate.api.domain.auth.dto.RegisterRequest;
import com.surveymate.api.domain.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface AuthMemberMapper {

    @Mappings({
            @Mapping(target = "memNum", ignore = true), // memNum은 직접 설정할 것이므로 무시
            @Mapping(target = "passwordError", ignore = true),
            @Mapping(target = "profileImageUuid", ignore = true),
            @Mapping(target = "createDate", ignore = true),
            @Mapping(target = "updateDate", ignore = true)
    })
    Member toEntity(RegisterRequest registerRequest);

}
