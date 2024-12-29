package com.surveymate.api.common.converter;

import com.surveymate.api.common.enums.MemberStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class MemberStatusConverter implements AttributeConverter<MemberStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(MemberStatus attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getValue();
    }

    @Override
    public MemberStatus convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return MemberStatus.fromValue(dbData);
    }

}
