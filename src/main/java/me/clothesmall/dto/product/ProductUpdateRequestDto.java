package me.clothesmall.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import me.clothesmall.domain.admin.Admin;
import me.clothesmall.domain.product.Product;
import me.clothesmall.domain.product.ProductCategoryDetail;

import javax.validation.constraints.NotEmpty;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdateRequestDto {
    @NotEmpty(message = "상품명을 입력해주세요.")
    private String name;

    @NotEmpty(message = "상품 가격을 입력해주세요.")
    @JsonProperty(value = "cost_price")
    private Integer costPrice;

    @NotEmpty(message = "상품 카테고리 상세를 입력해주세요.")
    @JsonProperty(value = "category_detail")
    private Long categoryDetail;

    @NotEmpty(message = "상품 판매 가격을 입력해주세요.")
    @JsonProperty(value = "selling_price")
    private Integer sellingPrice;

    @NotEmpty(message = "상품 정보를 입력해주세요.")
    @JsonProperty(value = "product_information")
    private String productInformation;

    private String status;

    @Setter
    private Admin admin;

    @Setter
    private ProductCategoryDetail productCategoryDetail;
}
