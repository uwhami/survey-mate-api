package com.surveymate.api.common.converter;

import com.surveymate.api.common.enums.MemberRole;
import jakarta.persistence.AttributeConverter;

public class MemberRoleConverter implements AttributeConverter<MemberRole, Integer> {

    @Override
    public Integer convertToDatabaseColumn(MemberRole attribute) {
        return attribute != null ? attribute.getCode() : null;
    }

    @Override
    public MemberRole convertToEntityAttribute(Integer dbData) {
        return dbData != null ? MemberRole.fromCode(dbData) : null;
    }

}
