package com.surveymate.api.domain.member.mapper;

import com.surveymate.api.domain.member.dto.MemberRequest;
import com.surveymate.api.domain.member.dto.MemberResponse;
import com.surveymate.api.domain.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    @Mappings({
            @Mapping(target = "profileImageUri", ignore = true),
            @Mapping(target = "userName", source = "userName")
    })
    MemberResponse toDTO(Member member);


    @Mappings({
            @Mapping(target = "memNum", ignore = true),
            @Mapping(target = "password", ignore = true),
            @Mapping(target = "profileImageUuid", ignore = true),
            @Mapping(target = "passwordError", ignore = true),
            @Mapping(target = "memStatus", ignore = true),
            @Mapping(target = "memRole", ignore = true),
            @Mapping(target = "socialType", ignore = true),
            @Mapping(target = "group", ignore = true),
            @Mapping(target = "joinDate", ignore = true),
            @Mapping(target = "deactivatedDate", ignore = true)
    })
    Member toEntity(MemberRequest memberRequest);

}

