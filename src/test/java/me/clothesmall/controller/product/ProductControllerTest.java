package me.clothesmall.controller.product;

import me.clothesmall.domain.IsDeletedTypeEnum;
import me.clothesmall.dto.common.ApiResponseTemplate;
import me.clothesmall.dto.product.*;
import me.clothesmall.service.product.ProductService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.net.URI;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProductService productService;

    @Ignore
    @Test
    public void create() throws Exception {
        ProductCreateRequestDto productCreateRequestDto = ProductCreateRequestDto.builder()
                .name("상품이름")
                .costPrice(500)
                .categoryDetail(1L)
                .sellingPrice(500)
                .productInformation("상품정보")
                .build();

        ProductCreateResponseDto productCreateResponseDtoMock = ProductCreateResponseDto.builder()
                .id(1L)
                .name("상품이름")
                .costPrice(500)
                .category("상의")
                .categoryDetail("긴팔")
                .sellingPrice(500)
                .productInformation("상품정보")
                .adminId(1)
                .adminName("홍길동")
                .isDeleted(IsDeletedEnum.N)
                .build();

        ApiResponseTemplate<ProductCreateResponseDto> productCreateResponseDto = new ApiResponseTemplate<>(HttpStatus.OK.value(), "OK", productCreateResponseDtoMock);
        given(productService.create(productCreateRequestDto)).willReturn(productCreateResponseDto);
        URI location = new URI("/api/products/" + productCreateResponseDto.getData().getId());

        String url = "/api/products/"+productCreateResponseDto.getData().getId();

        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content("{\n" +
                    "        \"name\" : \"상품이름\",\n" +
                    "        \"cost_price\" : 500,\n" +
                    "        \"category\" : 1,\n" +
                    "        \"category_detail\" : 1,\n" +
                    "        \"selling_price\" : 500,\n" +
                    "        \"product_information\" : \"상품정보\"\n" +
                "}")
        ).andExpect(status().isCreated())
        .andDo(print());
//
//        mockMvc.perform(post("/api/products")
//            .contentType(MediaType.APPLICATION_JSON)
//            .characterEncoding("utf-8")
//            .content("{\n" +
//                    "        \"name\" : \"상품이름\",\n" +
//                    "        \"cost_price\" : 500,\n" +
//                    "        \"category\" : 1,\n" +
//                    "        \"category_detail\" : 1,\n" +
//                    "        \"selling_price\" : 500,\n" +
//                    "        \"product_information\" : \"상품정보\"\n" +
//                    "}"))
//            .andExpect(status().isCreated())
//            .andExpect(header().string("location", "/api/products/1"));

//        mockMvc.perform(post("/api/products")
//            .contentType(MediaType.APPLICATION_JSON)
//            .characterEncoding("utf-8")
//            .content("{\n" +
//                    "        \"name\" : \"상품이름\",\n" +
//                    "        \"cost_price\" : 500,\n" +
//                    "        \"category\" : 1,\n" +
//                    "        \"category_detail\" : 1,\n" +
//                    "        \"selling_price\" : 500,\n" +
//                    "        \"product_information\" : \"상품정보\"\n" +
//                    "}"))
//            .andExpect(status().isCreated())
//            .andExpect(content().string("{\n" +
//                    "  \"code\" : 200,\n" +
//                    "  \"message\" : \"OK\",\n" +
//                    "  \"data\" : {\n" +
//                    "      \"id\" : 1,\n" +
//                    "      \"name\" : \"상품이름\",\n" +
//                    "      \"cost_price\" : 500,\n" +
//                    "      \"category\" : \"상의\",\n" +
//                    "      \"category_detail\" : \"긴팔\",\n" +
//                    "      \"selling_price\" : 500,\n" +
//                    "      \"product_information\" : \"상품정보\",\n" +
//                    "      \"admin_id\" : 1,\n" +
//                    "      \"admin_name\" : \"홍길동\",\n" +
//                    "      \"status\" : \"\",\n" +
//                    "      \"is_deleted\" : \"N\""+
//                    "  }\n" +
//                    "}"))
//                .andDo(print());
//        verify(productService).create(refEq(productCreateRequestDto));
    }

    @Test
    public void update() throws Exception {
        Long id = 1L;
        ProductUpdateRequestDto productUpdateRequestDto = ProductUpdateRequestDto.builder()
                .name("상품이름수정")
                .costPrice(500)
                .categoryDetail(1L)
                .sellingPrice(500)
                .productInformation("상품정보수정")
                .status("")
                .build();

        ProductUpdateResponseDto productUpdateResponseDto = ProductUpdateResponseDto.builder()
                .id(1L)
                .name("상품이름수정")
                .costPrice(500)
                .category("상의")
                .categoryDetail("긴팔")
                .sellingPrice(500)
                .productInformation("상품정보수정")
                .adminId(1)
                .adminName("홍길동")
                .isDeleted(IsDeletedEnum.N)
                .build();

        given(productService.update(id, productUpdateRequestDto)).willReturn(productUpdateResponseDto);

        final ResultActions actions = mockMvc.perform(patch("/api/product/"+id)
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"name\" : \"상품이름수정\",\n" +
                        "    \"product_information\" : \"상품정보수정\",\n" +
                        "    \"cost_price\" : 500,\n" +
                        "    \"selling_price\" : 500,\n" +
                        "    \"status\" : \"\",\n" +
                        "    \"category_detail\" : 1\n" +
                        "}")).andDo(print());

        actions.andExpect(status().isOk());
    }

    @Test
    public void list() throws Exception {
        ProductListRequestDto productListRequestDto = ProductListRequestDto.builder()
                .size(10)
                .page(0)
                .isDeleted(IsDeletedTypeEnum.N)
                .build();

        ProductListResponseDto productListResponseDto = ProductListResponseDto.builder().build();

        given(productService.list(productListRequestDto)).willReturn(productListResponseDto);

        final ResultActions actions = mockMvc.perform(get("/api/products")
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"page\" : 0,\n" +
                        "    \"size\" : 10,\n" +
                        "    \"is_deleted\" : \"Y\"\n" +
                        "}")).andDo(print());

        actions.andExpect(status().isOk());
    }

    @Test
    public void 삭제() throws Exception {
        // given
        Long id = 1L;
        ProductDeleteResponseDto productDeleteResponseDto = ProductDeleteResponseDto.builder()
                .id(id)
                .name("상품이름수정")
                .costPrice(500)
                .category("상의")
                .categoryDetail("긴팔")
                .sellingPrice(500)
                .productInformation("상품정보수정")
                .adminId(1)
                .adminName("홍길동")
                .isDeleted(IsDeletedEnum.Y)
                .build();

        given(productService.delete(id)).willReturn(productDeleteResponseDto);

        // when
        final ResultActions actions = mockMvc.perform(delete("/api/product/{id}", id)
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON)).andDo(print());

        // then
        actions.andExpect(status().isOk());
    }
}
