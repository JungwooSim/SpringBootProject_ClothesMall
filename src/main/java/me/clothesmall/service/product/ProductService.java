package me.clothesmall.service.product;

import lombok.RequiredArgsConstructor;
import me.clothesmall.domain.IsDeletedTypeEnum;
import me.clothesmall.domain.admin.Admin;
import me.clothesmall.domain.admin.AdminRepository;
import me.clothesmall.dto.common.ApiResponseTemplate;
import me.clothesmall.domain.product.*;
import me.clothesmall.dto.product.ProductCreateRequestDto;
import me.clothesmall.dto.product.ProductCreateResponseDto;
import me.clothesmall.dto.product.ProductUpdateRequestDto;
import me.clothesmall.dto.product.ProductUpdateResponseDto;
import me.clothesmall.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductCategoryDetailRepository productCategoryDetailRepository;
    private final AdminRepository adminRepository;

    @Transactional
    public ApiResponseTemplate<ProductCreateResponseDto> create(ProductCreateRequestDto productCreateRequestDto) {
        // Todo:: Admin 개발 되면 수정 필요
        Long AdminId = 1L;

        Admin admin = adminRepository.findById(AdminId).orElseGet(Admin::new);
        ProductCategoryDetail productCategoryDetail = productCategoryDetailRepository.findById(1L).orElseGet(ProductCategoryDetail::new);

        Product saveProduct = Product.builder()
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

        Product product = productRepository.save(saveProduct);

        IsDeletedTypeEnum isDeletedTypeEnum = IsDeletedTypeEnum.N;
        isDeletedTypeEnum.responseIsDeleted();

        ProductCreateResponseDto productCreateResponseDto = ProductCreateResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .costPrice(product.getCostPrice())
                .category(productCategoryDetail.getProductCategory().getName())
                .categoryDetail(productCategoryDetail.getName())
                .sellingPrice(product.getSellingPrice())
                .productInformation(product.getProductInformation())
                .adminName(product.getAdmin().getName())
                .adminId(admin.getId())
                .status("")
                .modifiedDate(product.getModifiedDate())
                .createdDate(product.getCreatedDate())
                .isDeleted(isDeletedTypeEnum.responseIsDeleted())
                .build();

        ApiResponseTemplate<ProductCreateResponseDto> response = new ApiResponseTemplate<>(HttpStatus.OK.value(), "OK", productCreateResponseDto);
        return response;
    }

    @Transactional
    public ProductUpdateResponseDto update(Long id, ProductUpdateRequestDto productUpdateRequestDto) {
        Product product = productRepository.findById(id).orElseThrow(() -> new BusinessException("유효하지 않은 게시글입니다."));

        Admin admin = adminRepository.findById(id).orElseThrow(
                () -> new BusinessException("유요하지 않은 사용자 입니다.")
        );

        ProductCategoryDetail productCategoryDetail = productCategoryDetailRepository.findById(productUpdateRequestDto.getCategoryDetail()).orElseThrow(
                () -> new BusinessException("유효하지 않은 상세 카테고리 항목입니다.")
        );
        ProductCategory productCategory = productCategoryDetail.getProductCategory();

        product.changeContent(
                productUpdateRequestDto.getName(),
                productUpdateRequestDto.getCostPrice(),
                productUpdateRequestDto.getSellingPrice(),
                productUpdateRequestDto.getProductInformation(),
                productUpdateRequestDto.getStatus(),
                admin,
                productCategoryDetail
        );

        productRepository.save(product);

        ProductUpdateResponseDto productUpdateResponseDto = ProductUpdateResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .costPrice(product.getCostPrice())
                .category(product.getProductCategoryDetail().getProductCategory().getName())
                .categoryDetail(product.getProductCategoryDetail().getName())
                .sellingPrice(product.getSellingPrice())
                .productInformation(product.getProductInformation())
                .adminId(product.getAdmin().getId())
                .adminName(product.getAdmin().getName())
                .isDeleted(product.getIsDeleted().responseIsDeleted())
                .createdDate(product.getCreatedDate())
                .modifiedDate(product.getModifiedDate())
                .status(product.getStatus())
                .build();

        return productUpdateResponseDto;
    }
}
