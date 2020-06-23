package me.clothesmall.service.product;

import lombok.RequiredArgsConstructor;
import me.clothesmall.domain.IsDeletedTypeEnum;
import me.clothesmall.domain.admin.Admin;
import me.clothesmall.domain.admin.AdminRepository;
import me.clothesmall.domain.common.ApiResponseTemplate;
import me.clothesmall.domain.product.*;
import me.clothesmall.domain.product.dto.ProductCreateRequestDto;
import me.clothesmall.domain.product.dto.ProductCreateResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductCategoryDetailRepository productCategoryDetailRepository;
    private final AdminRepository adminRepository;

    @Transactional
    public ApiResponseTemplate<ProductCreateResponseDto> create(ProductCreateRequestDto productCreateRequestDto) {

        Admin admin = adminRepository.findById(1L).orElseGet(Admin::new);
        ProductCategoryDetail productCategoryDetail = productCategoryDetailRepository.findById(1L).orElseGet(ProductCategoryDetail::new);
        productCategoryDetail.getProductCategory();

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
                .adminName(admin.getName())
                .status("")
                .isDeleted(isDeletedTypeEnum.responseIsDeleted())
                .build();

        ApiResponseTemplate<ProductCreateResponseDto> response = new ApiResponseTemplate<>(HttpStatus.OK, "OK", productCreateResponseDto);
        return response;
    }
}
