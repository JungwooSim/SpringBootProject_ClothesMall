package me.clothesmall.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.clothesmall.domain.IsDeletedTypeEnum;

import javax.validation.constraints.Size;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductListRequestDto {

    private Integer page = 0;

    @Size(max = 20,message = "size는 20 이하로 가능합니다.")
    private Integer size = 5;

    @JsonProperty(value = "is_deleted")
    private IsDeletedTypeEnum isDeleted = IsDeletedTypeEnum.N;
}

