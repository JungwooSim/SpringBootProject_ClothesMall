package me.clothesmall.domain.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.clothesmall.config.enum_converter.IsDeletedOfEntityConverter;
import me.clothesmall.domain.IsDeletedTypeEnum;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_category")
@Entity
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer priority;

    @Convert(converter = IsDeletedOfEntityConverter.class)
    @Column(name = "is_deleted")
    private IsDeletedTypeEnum isDeleted;

    @Column(name="modified_date", insertable = false, updatable = false)
    private LocalDateTime modifiedDate;

    @Column(name="created_date", insertable = false, updatable = false)
    private LocalDateTime createdDate;
}
/*
CREATE TABLE `product_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `priority` int(11) NOT NULL DEFAULT '0' COMMENT '정렬',
  `is_deleted` tinyint(9) NOT NULL DEFAULT '1',
  `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
 */
