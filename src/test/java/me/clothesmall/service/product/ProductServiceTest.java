package me.clothesmall.service.product;


import me.clothesmall.domain.IsDeletedTypeEnum;
import me.clothesmall.domain.admin.Admin;
import me.clothesmall.domain.admin.AdminRepository;
import me.clothesmall.dto.common.ApiResponseTemplate;
import me.clothesmall.domain.product.*;
import me.clothesmall.dto.product.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;
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
        // given
        ProductCreateRequestDto productCreateRequestDto = createProductCreateRequestDto();
        Admin admin = createAdmin();
        ProductCategoryDetail productCategoryDetail = createProductCategoryDetail();

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

        // when
        ProductCreateResponseDto productCreateResponseDto = ProductCreateResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .costPrice(product.getCostPrice())
                .category(product.getProductCategoryDetail().getProductCategory().getName())
                .categoryDetail(productCategoryDetail.getName())
                .sellingPrice(product.getSellingPrice())
                .productInformation(product.getProductInformation())
                .adminName(admin.getName())
                .adminId(admin.getId())
                .status("")
                .modifiedDate(product.getModifiedDate())
                .createdDate(product.getCreatedDate())
                .isDeleted(product.getIsDeleted().responseIsDeleted())
                .build();

        ApiResponseTemplate<ProductCreateResponseDto> response = new ApiResponseTemplate<>(HttpStatus.OK.value(), "OK", productCreateResponseDto);

        // then
        assertThat(product.getIsDeleted(), is(IsDeletedTypeEnum.N));
        assertThat(response.getMessage(), is("OK"));
        assertThat(response.getData().getId(), is(1L));
        assertThat(response.getData().getAdminName(), is("홍길동"));
        assertThat(response.getData().getIsDeleted(), is(IsDeletedEnum.N));
    }

    @Test
    public void update() {
        Long id = 1L;
        // given
        ProductUpdateRequestDto productUpdateRequestDto = ProductUpdateRequestDto.builder()
                .name("상품이름수정")
                .costPrice(500)
                .categoryDetail(1L)
                .sellingPrice(500)
                .productInformation("상품정보수정")
                .status("")
                .build();

        Admin admin = createAdmin();
        ProductCategoryDetail productCategoryDetail = createProductCategoryDetail();


        Product product = Product.builder()
                .id(1L)
                .name("상품이름")
                .costPrice(100)
                .sellingPrice(100)
                .productInformation("상품정보")
                .admin(Admin.builder().build())
                .productCategoryDetail(ProductCategoryDetail.builder().build())
                .isDeleted(IsDeletedTypeEnum.N)
                .build();

        given(productRepository.findById(id)).willReturn(Optional.of(product));

        given(adminRepository.findById(1L)).willReturn(Optional.of(admin));
        given(productCategoryDetailRepository.findById(1L)).willReturn(Optional.of(productCategoryDetail));

        product.changeContent(
                productUpdateRequestDto.getName(),
                productUpdateRequestDto.getCostPrice(),
                productUpdateRequestDto.getSellingPrice(),
                productUpdateRequestDto.getProductInformation(),
                productUpdateRequestDto.getStatus(),
                productUpdateRequestDto.getAdmin(),
                productCategoryDetail
        );

        // update 된것 그대로 받기
        // when
        given(productRepository.save(product)).will(invocation -> {
            Product updatedProduct = invocation.getArgument(0);
            return updatedProduct;
        });

        ProductUpdateResponseDto productUpdateResponseDto = ProductUpdateResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .costPrice(product.getCostPrice())
                .category(product.getProductCategoryDetail().getProductCategory().getName())
                .categoryDetail(product.getProductCategoryDetail().getName())
                .sellingPrice(product.getSellingPrice())
                .productInformation(product.getProductInformation())
                .adminId(admin.getId())
                .adminName(admin.getName())
                .isDeleted(product.getIsDeleted().responseIsDeleted())
                .createdDate(product.getCreatedDate())
                .modifiedDate(product.getModifiedDate())
                .status(product.getStatus())
                .build();

        // then
        assertThat(productUpdateResponseDto.getName(), is(productUpdateRequestDto.getName()));
    }

    @Test
    public void list() {
        // given

        // pageable 셋팅
        ProductListRequestDto productListRequestDto = ProductListRequestDto.builder()
                .size(10)
                .page(0)
                .isDeleted(IsDeletedTypeEnum.N)
                .build();

        Pageable pageable = PageRequest.of(
                productListRequestDto.getPage(),
                productListRequestDto.getSize(),
                Sort.by("createdDate").descending()
        );

        // 리스트 호출
        ArrayList<Product> productsMock = new ArrayList<>();
        for (int i = 0; i <= 15; i++) {
            productsMock.add(
                    Product.builder()
                            .id(Long.valueOf(i))
                            .name("게시판이름")
                            .costPrice(100)
                            .sellingPrice(100)
                            .productInformation("게시판정보")
                            .status("")
                            .isDeleted(IsDeletedTypeEnum.N)
                            .modifiedDate(LocalDateTime.now())
                            .createdDate(LocalDateTime.now())
                            .admin(createAdmin())
                            .productCategoryDetail(createProductCategoryDetail())
                            .build()
            );
        }
        Page<Product> products = new PageImpl(productsMock, pageable, productsMock.size());
        given(productRepository.findByIsDeleted(IsDeletedTypeEnum.N, pageable)).willReturn(products);

        ArrayList<ProductListDetailContentsDto> productDetailContent = new ArrayList<>();
        // 리스트를 리스판스에 매칭
        products.getContent().stream().forEach(
                product ->
                    productDetailContent.add(
                            ProductListDetailContentsDto.builder()
                                    .id(product.getId())
                                    .name(product.getName())
                                    .costPrice(product.getCostPrice())
                                    .sellingPrice(product.getSellingPrice())
                                    .category(product.getProductCategoryDetail().getProductCategory().getName())
                                    .categoryDetail(product.getProductCategoryDetail().getName())
                                    .adminId(product.getAdmin().getId())
                                    .adminName(product.getAdmin().getName())
                                    .status(product.getStatus())
                                    .isDeleted(product.getIsDeleted().responseIsDeleted())
                                    .createdDate(product.getCreatedDate())
                                    .modifiedDate(product.getModifiedDate())
                                    .build()
                    )
        );

        ProductListResponseDto productListResponseDto = ProductListResponseDto.builder()
                .page(products.getPageable().getPageNumber())
                .size(products.getSize())
                .totalCount(products.getTotalElements())
                .detailContents(productDetailContent)
                .build();

        assertThat(productListResponseDto.getSize(), is(10));
        assertTrue(productListResponseDto.getDetailContents().size() >= 10);
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
//                .isDeleted(IsDeletedTypeEnum.N)
                .status("")
                .lastLoginDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .createdDate(LocalDateTime.now())
                .build();
    }
}
