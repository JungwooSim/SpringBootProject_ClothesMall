package me.clothesmall.config.enum_converter;

import me.clothesmall.domain.IsDeletedTypeEnum;

import javax.persistence.AttributeConverter;

public class IsDeletedOfEntityConverter implements AttributeConverter<IsDeletedTypeEnum, Integer> {
    @Override
    public Integer convertToDatabaseColumn(IsDeletedTypeEnum attribute) {
        if (attribute == null)
            return null;
        return attribute.getValue();
    }

    @Override
    public IsDeletedTypeEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null)
            return null;
        return IsDeletedTypeEnum.enumOf(dbData);
    }
}
