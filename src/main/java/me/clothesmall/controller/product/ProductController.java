package me.clothesmall.controller.product;

import lombok.RequiredArgsConstructor;
import me.clothesmall.domain.IsDeletedTypeEnum;
import me.clothesmall.dto.common.ApiResponseTemplate;
import me.clothesmall.dto.product.*;
import me.clothesmall.service.product.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        ApiResponseTemplate<ProductCreateResponseDto> responseResource = productService.create(productCreateRequestDto);

        URI location = new URI("/api/products/" + responseResource.getData().getId());
        return ResponseEntity.created(location).body(responseResource);
    }

    @PatchMapping("/api/product/{id}")
    public ResponseEntity<ApiResponseTemplate<ProductUpdateResponseDto>> update(
            @PathVariable("id") Long id,
            @Valid @RequestBody ProductUpdateRequestDto productUpdateRequestDto
    ) {
        ProductUpdateResponseDto productUpdateResponseDto = productService.update(id, productUpdateRequestDto);
        ApiResponseTemplate<ProductUpdateResponseDto> responseResource = ApiResponseTemplate.<ProductUpdateResponseDto>builder()
                    .data(productUpdateResponseDto)
                    .code(HttpStatus.OK.value())
                    .message("OK")
                    .build();

        return ResponseEntity.ok(responseResource);
    }

    @GetMapping("/api/products")
    public ResponseEntity<ApiResponseTemplate<ProductListResponseDto>> list(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "is_Deleted", defaultValue = "N") IsDeletedTypeEnum isDeleted
//            @Valid @RequestParam ProductListRequestDto productListRequestDto
            ) {
        ProductListRequestDto productListRequestDto = ProductListRequestDto.builder()
                .page(page)
                .size(size)
                .isDeleted(isDeleted)
                .build();

        ProductListResponseDto productListResponseDto = productService.list(productListRequestDto);
        ApiResponseTemplate<ProductListResponseDto> responseResource = ApiResponseTemplate.<ProductListResponseDto>builder()
                .data(productListResponseDto)
                .code(HttpStatus.OK.value())
                .message("OK")
                .build();

        return ResponseEntity.ok(responseResource);
    }

    @DeleteMapping("/api/product/{id}")
    public ResponseEntity<ApiResponseTemplate<ProductDeleteResponseDto>> delete(
            @PathVariable("id") Long id
    ) {
        ProductDeleteResponseDto productDeleteResponseDto = productService.delete(id);

        ApiResponseTemplate<ProductDeleteResponseDto> responseResource = ApiResponseTemplate.<ProductDeleteResponseDto>builder()
                .data(productDeleteResponseDto)
                .code(HttpStatus.OK.value())
                .message("OK")
                .build();

        return ResponseEntity.ok(responseResource);
    }
}
