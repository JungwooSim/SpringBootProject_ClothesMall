package me.clothesmall.domain;

import me.clothesmall.domain.product.dto.IsDeletedEnum;

public enum IsDeletedTypeEnum {
    N(1),
    Y(2);

    private Integer isDeleted;

    IsDeletedTypeEnum(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public IsDeletedEnum responseIsDeleted() {
        if (isDeleted == 'N') {
           return IsDeletedEnum.N;
        } else {
            return IsDeletedEnum.Y;
        }
    }
}
