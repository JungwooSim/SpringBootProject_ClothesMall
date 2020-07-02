package me.clothesmall.domain.product;

import me.clothesmall.domain.IsDeletedTypeEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("dev")
public class ProductRepositoryTest {
    @Autowired
    ProductRepository productRepository;

    @Test
    public void list() {
        Pageable pageable = PageRequest.of(0, 3, Sort.by("createdDate").descending());

        Page<Product> products = productRepository.findByIsDeleted(IsDeletedTypeEnum.N, pageable);

        assertTrue(products.getSize() > 1);
    }
}
