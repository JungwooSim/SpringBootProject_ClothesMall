package me.clothesmall.domain.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

import lombok.NoArgsConstructor;
import me.clothesmall.config.enum_converter.IsDeletedOfEntityConverter;
import me.clothesmall.domain.IsDeletedTypeEnum;
import me.clothesmall.domain.admin.Admin;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "cost_price")
    private Integer costPrice;

    @Column(name = "selling_price")
    private Integer sellingPrice;

    @Column(name = "product_information")
    private String productInformation;

    private String status;

    @Convert(converter = IsDeletedOfEntityConverter.class)
    @Column(name = "is_deleted")
    private IsDeletedTypeEnum isDeleted;

    @Column(name="modified_date", insertable = false, updatable = false)
    private LocalDateTime modifiedDate;

    @Column(name="created_date", insertable = false, updatable = false)
    private LocalDateTime createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id", referencedColumnName = "id")
    private Admin admin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_category_detail_id", referencedColumnName = "id")
    private ProductCategoryDetail productCategoryDetail;

    public void changeContent(
            String name, Integer costPrice, Integer sellingPrice,
            String productInformation, String status, Admin admin,
            ProductCategoryDetail productCategoryDetail) {
        this.name = name;
        this.costPrice = costPrice;
        this.sellingPrice = sellingPrice;
        this.productInformation = productInformation;
        this.status = status;
        this.admin = admin;
        this.productCategoryDetail = productCategoryDetail;
    }
}
