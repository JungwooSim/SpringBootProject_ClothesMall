package me.clothesmall.dto.product;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductListRequestDto {
    private Integer page;
    private Integer size;
    private IsDeletedEnum isDeleted = IsDeletedEnum.N;

    @Builder
    public ProductListRequestDto(Integer page, Integer size, IsDeletedEnum isDeleted) {
        this.page = page;
        this.size = size;
        this.isDeleted = isDeleted;
    }
}

