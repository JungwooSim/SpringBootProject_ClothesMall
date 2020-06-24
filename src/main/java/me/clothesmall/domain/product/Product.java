package me.clothesmall.domain.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

import me.clothesmall.config.enum_converter.IsDeletedOfEntityConverter;
import me.clothesmall.domain.IsDeletedTypeEnum;
import me.clothesmall.domain.admin.Admin;

@Getter
@Builder
@AllArgsConstructor
@Table(name = "product")
@Entity
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
}
/*
CREATE TABLE `product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `cost_price` int(11) NOT NULL DEFAULT '0',
  `product_category_detail_id` int(11) NOT NULL,
  `selling_price` int(11) NOT NULL DEFAULT '0',
  `product_information` text,
  `admin_id` int(11) NOT NULL,
  `status` varchar(10) DEFAULT NULL,
  `is_deleted` tinyint(9) NOT NULL DEFAULT '1',
  `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `FK_product_product_category_detail_id_product_category_detail_id` (`product_category_detail_id`),
  CONSTRAINT `FK_product_product_category_detail_id_product_category_detail_id` FOREIGN KEY (`product_category_detail_id`) REFERENCES `product_category_detail` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='상품'
 */
