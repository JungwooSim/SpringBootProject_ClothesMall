package me.clothesmall.domain.product;

import me.clothesmall.domain.IsDeletedTypeEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class ProductCategoryDetailRepositoryTest {
    @Autowired
    ProductCategoryDetailRepository productCategoryDetailRepository;

    @Test
    @Transactional
    public void findById() {
        ProductCategoryDetail productCategoryDetail = productCategoryDetailRepository.findById(1L).orElseGet(ProductCategoryDetail::new);

        assertThat(productCategoryDetail.getId()).isEqualTo(1);
        assertThat(productCategoryDetail.getProductCategory().getName()).isEqualTo("상의");
    }
}