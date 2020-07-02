package me.clothesmall.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductListResponseDto {
    private Integer page;

    private Integer size;

    @JsonProperty(value = "total_count")
    private Long totalCount;

    @JsonProperty(value = "list")
    private ArrayList<ProductListDetailContentsDto> detailContents;
}


