package me.clothesmall.domain.product;

import me.clothesmall.domain.IsDeletedTypeEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository extends JpaRepository<Product, Long>, PagingAndSortingRepository<Product, Long> {
    Page<Product> findByIsDeleted(IsDeletedTypeEnum isDeleted, Pageable pageable);
}
