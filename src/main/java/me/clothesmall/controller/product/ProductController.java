package me.clothesmall.controller.product;

import lombok.RequiredArgsConstructor;
import me.clothesmall.domain.common.ApiResponseTemplate;
import me.clothesmall.domain.product.dto.ProductCreateRequestDto;
import me.clothesmall.domain.product.dto.ProductCreateResponseDto;
import me.clothesmall.service.product.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RequiredArgsConstructor
@RequestMapping(produces = "application/json; charset=UTF-8")
@RestController
public class ProductController {
    private final ProductService productService;

    @PostMapping(value = "/api/products")
    public ResponseEntity<ApiResponseTemplate<ProductCreateResponseDto>> create(@Valid @RequestBody ProductCreateRequestDto productCreateRequestDto) throws URISyntaxException {
        ApiResponseTemplate<ProductCreateResponseDto> productCreateResponseDto = productService.create(productCreateRequestDto);

        URI location = new URI("/api/products/" + productCreateResponseDto.getData().getId());
        return ResponseEntity.created(location).body(productCreateResponseDto);
    }
}
