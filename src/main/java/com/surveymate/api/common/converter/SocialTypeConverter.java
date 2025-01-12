package com.surveymate.api.common.converter;

import com.surveymate.api.common.enums.SocialType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class SocialTypeConverter implements AttributeConverter<SocialType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(SocialType attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getValue();
    }

    @Override
    public SocialType convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return SocialType.fromValue(dbData);
    }

}
