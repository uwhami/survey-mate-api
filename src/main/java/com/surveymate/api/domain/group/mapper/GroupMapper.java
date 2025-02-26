package com.surveymate.api.domain.group.mapper;

import com.surveymate.api.domain.group.dto.GroupReponse;
import com.surveymate.api.domain.group.entity.Group;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GroupMapper {

    GroupReponse toDto(Group group);
}
