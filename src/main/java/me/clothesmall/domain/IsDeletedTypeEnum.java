package me.clothesmall.domain;

import me.clothesmall.dto.product.IsDeletedEnum;

import java.util.Arrays;

public enum IsDeletedTypeEnum {
    N(1),
    Y(2);

    private Integer isDeleted;

    IsDeletedTypeEnum(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public static IsDeletedTypeEnum enumOf(int n) {
        return Arrays.stream(IsDeletedTypeEnum.values())
                .filter(t -> t.getValue() == n)
                .findAny().orElse(null);
    }

    public IsDeletedEnum responseIsDeleted() {
        if (isDeleted == 1) {
           return IsDeletedEnum.N;
        } else {
            return IsDeletedEnum.Y;
        }
    }

    public Integer getValue() {
        return this.isDeleted;
    }
}
