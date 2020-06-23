package me.clothesmall.domain.admin;

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
@Table(name = "admin")
@Entity
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String aid;

    private String password;

    private String name;

    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "is_deleted")
    private IsDeletedTypeEnum isDeleted;

    private String status;

    @Column(name = "last_login_date")
    private LocalDateTime lastLoginDate;

    @Column(name="modified_date", insertable = false, updatable = false)
    private LocalDateTime modifiedDate;

    @Column(name="created_date", insertable = false, updatable = false)
    private LocalDateTime createdDate;
}
/*
CREATE TABLE `admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `aid` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `name` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `phone_number` varchar(20) NOT NULL,
  `is_deleted` tinyint(9) NOT NULL DEFAULT '1',
  `status` varchar(10) NOT NULL,
  `last_login_date` datetime NOT NULL,
  `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UC_aid` (`aid`),
  UNIQUE KEY `UC_email` (`email`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='관리자'
 */
