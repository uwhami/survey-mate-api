package com.surveymate.api.member.mapper;

import com.surveymate.api.member.dto.MemberDTO;
import com.surveymate.api.member.dto.MemberSignupDTO;
import com.surveymate.api.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface MemberMapper {
    @Mappings({
            @Mapping(target = "profileImage", ignore = true)
    })
    MemberDTO toDTO(Member member);

    @Mappings({
            @Mapping(target = "memNum", ignore = true), // memNum은 직접 설정할 것이므로 무시
            @Mapping(target = "password", ignore = true),
            @Mapping(target = "profileImageUuid", ignore = true),
            @Mapping(target = "passwordError", ignore = true),
            @Mapping(target = "createDate", ignore = true),
            @Mapping(target = "updateDate", ignore = true)
    })
    Member toEntity(MemberDTO memberDTO);


    @Mappings({
            @Mapping(target = "memNum", ignore = true), // memNum은 직접 설정할 것이므로 무시
            @Mapping(target = "passwordError", ignore = true),
            @Mapping(target = "createDate", ignore = true),
            @Mapping(target = "updateDate", ignore = true)
    })
    Member toEntity(MemberSignupDTO memberSignupDTO);
}

