package me.clothesmall.service.product;


import me.clothesmall.domain.IsDeletedTypeEnum;
import me.clothesmall.domain.admin.Admin;
import me.clothesmall.domain.admin.AdminRepository;
import me.clothesmall.domain.common.ApiResponseTemplate;
import me.clothesmall.domain.product.*;
import me.clothesmall.domain.product.dto.IsDeletedEnum;
import me.clothesmall.domain.product.dto.ProductCreateRequestDto;
import me.clothesmall.domain.product.dto.ProductCreateResponseDto;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;

public class ProductServiceTest {
    ProductService productService;

    @Mock
    AdminRepository adminRepository;
    @Mock
    ProductRepository productRepository;
    @Mock
    ProductCategoryRepository productCategoryRepository;
    @Mock
    ProductCategoryDetailRepository productCategoryDetailRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        productService = new ProductService(
                productRepository,
                productCategoryRepository,
                productCategoryDetailRepository,
                adminRepository
        );
    }

    @Test
    public void create() {
        ProductCreateRequestDto productCreateRequestDto = createProductCreateRequestDto();
        Admin admin = createAdmin();
        ProductCategoryDetail productCategoryDetail = createProductCategoryDetail();
        ProductCategory productCategory = createProductCategory();

        given(adminRepository.findById(1L)).willReturn(Optional.of(admin));
        given(productCategoryDetailRepository.findById(1L)).willReturn(Optional.of(productCategoryDetail));

        Product product = Product.builder()
                .id(1L)
                .name(productCreateRequestDto.getName())
                .costPrice(productCreateRequestDto.getCostPrice())
                .sellingPrice(productCreateRequestDto.getSellingPrice())
                .productInformation(productCreateRequestDto.getProductInformation())
                .status("")
                .isDeleted(IsDeletedTypeEnum.N)
                .modifiedDate(LocalDateTime.now())
                .createdDate(LocalDateTime.now())
                .admin(admin)
                .productCategoryDetail(productCategoryDetail)
                .build();

        given(productRepository.save(product)).willReturn(product);

        IsDeletedTypeEnum isDeletedTypeEnum = IsDeletedTypeEnum.N;
        isDeletedTypeEnum.responseIsDeleted();

        ProductCreateResponseDto productCreateResponseDtoMock = ProductCreateResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .costPrice(product.getCostPrice())
                .category(productCategory.getName())
                .categoryDetail(productCategoryDetail.getName())
                .sellingPrice(product.getSellingPrice())
                .productInformation(product.getProductInformation())
                .adminName(admin.getName())
                .status("")
                .isDeleted(isDeletedTypeEnum.responseIsDeleted())
                .build();

        ApiResponseTemplate<ProductCreateResponseDto> response = new ApiResponseTemplate<ProductCreateResponseDto>(HttpStatus.OK, "OK", productCreateResponseDtoMock);

        ApiResponseTemplate<ProductCreateResponseDto> productCreateResponseDto = productService.create(productCreateRequestDto);

        assertThat(productCreateResponseDto.getMessage(), is("OK"));
    }

    private ProductCategory createProductCategory() {
        return ProductCategory.builder()
                .id(1L)
                .name("상의")
                .priority(1)
                .isDeleted(IsDeletedTypeEnum.N)
                .modifiedDate(LocalDateTime.now())
                .createdDate(LocalDateTime.now())
                .build();
    }

    private ProductCategoryDetail createProductCategoryDetail() {
        ProductCategoryDetail productCategoryDetail = ProductCategoryDetail.builder()
                .id(1L)
                .name("긴팔")
                .priority(1)
                .isDeleted(IsDeletedTypeEnum.N)
                .productCategory(createProductCategory())
                .modifiedDate(LocalDateTime.now())
                .createdDate(LocalDateTime.now())
                .build();
        return productCategoryDetail;
    }

    private ProductCreateRequestDto createProductCreateRequestDto() {
        return ProductCreateRequestDto.builder()
                .name("상품이름")
                .costPrice(500)
                .categoryDetail(1L)
                .sellingPrice(500)
                .productInformation("상품정보")
                .status("")
                .build();
    }

    private Admin createAdmin() {
        return Admin.builder()
                .id(1L)
                .aid("test")
                .password("test")
                .name("홍길동")
                .email("test@test.com")
                .phoneNumber("01012345678")
                .isDeleted(IsDeletedTypeEnum.N)
                .status("")
                .lastLoginDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .createdDate(LocalDateTime.now())
                .build();
    }
}
