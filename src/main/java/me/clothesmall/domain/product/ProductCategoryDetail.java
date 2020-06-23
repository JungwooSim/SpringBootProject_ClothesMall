package me.clothesmall.domain.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.clothesmall.domain.IsDeletedTypeEnum;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_category_detail")
@Entity
public class ProductCategoryDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer priority;

    @Column(name = "is_deleted")
    private IsDeletedTypeEnum isDeleted;

    @Column(name="modified_date", insertable = false, updatable = false)
    private LocalDateTime modifiedDate;

    @Column(name="created_date", insertable = false, updatable = false)
    private LocalDateTime createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_category_id", referencedColumnName = "id")
    private ProductCategory productCategory;
}
/*
CREATE TABLE `product_category_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `priority` int(11) NOT NULL,
  `is_deleted` tinyint(9) NOT NULL DEFAULT '1',
  `product_category_id` int(11) NOT NULL,
  `created_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `modified_date` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
 */
