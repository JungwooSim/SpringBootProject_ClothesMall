package me.clothesmall.controller.product;

import lombok.RequiredArgsConstructor;
import me.clothesmall.domain.common.ApiResponseTemplate;
import me.clothesmall.domain.product.dto.ProductCreateRequestDto;
import me.clothesmall.domain.product.dto.ProductCreateResponseDto;
import me.clothesmall.service.product.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class ProductController {
    private final ProductService productService;

    @PostMapping("/api/products")
    public ApiResponseTemplate<ProductCreateResponseDto> create(@Valid @RequestBody ProductCreateRequestDto productCreateRequestDto) {
        return productService.create(productCreateRequestDto);
    }
}
