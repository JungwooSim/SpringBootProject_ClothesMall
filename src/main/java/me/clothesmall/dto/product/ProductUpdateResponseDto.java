package me.clothesmall.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdateResponseDto {
    private Long id;

    private String name;

    @JsonProperty(value = "product_information")
    private String productInformation;

    @JsonProperty(value = "cost_price")
    private Integer costPrice;

    @JsonProperty(value = "selling_price")
    private Integer sellingPrice;

    private String status;

    @JsonProperty(value = "admin_id")
    private long adminId;

    private String category;

    @JsonProperty(value = "category_detail")
    private String categoryDetail;

    @JsonProperty(value = "admin_name")
    private String adminName;

    @JsonProperty(value = "is_deleted")
    private IsDeletedEnum isDeleted;

    private LocalDateTime modifiedDate;

    private LocalDateTime createdDate;
}
