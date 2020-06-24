package me.clothesmall.controller.product;

import me.clothesmall.domain.common.ApiResponseTemplate;
import me.clothesmall.domain.product.dto.IsDeletedEnum;
import me.clothesmall.domain.product.dto.ProductCreateRequestDto;
import me.clothesmall.domain.product.dto.ProductCreateResponseDto;
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

import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

        String url = "/api/products/"+productCreateResponseDto.getData().getId();

//        mockMvc.perform(post("/api/products")
//                .contentType(MediaType.APPLICATION_JSON)
//                .characterEncoding("utf-8")
//                .content("{\n" +
//                    "        \"name\" : \"상품이름\",\n" +
//                    "        \"cost_price\" : 500,\n" +
//                    "        \"category\" : 1,\n" +
//                    "        \"category_detail\" : 1,\n" +
//                    "        \"selling_price\" : 500,\n" +
//                    "        \"product_information\" : \"상품정보\"\n" +
//                "}")
//        ).andExpect(status().isCreated())
//        .andDo(print());
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
                    "}"))
            .andExpect(status().isCreated())
            .andExpect(content().string("{\n" +
                    "  \"code\" : 200,\n" +
                    "  \"message\" : \"OK\",\n" +
                    "  \"data\" : {\n" +
                    "      \"id\" : 1,\n" +
                    "      \"name\" : \"상품이름\",\n" +
                    "      \"cost_price\" : 500,\n" +
                    "      \"category\" : \"상의\",\n" +
                    "      \"category_detail\" : \"긴팔\",\n" +
                    "      \"selling_price\" : 500,\n" +
                    "      \"product_information\" : \"상품정보\",\n" +
                    "      \"admin_id\" : 1,\n" +
                    "      \"admin_name\" : \"홍길동\",\n" +
                    "      \"status\" : \"\",\n" +
                    "      \"is_deleted\" : \"N\""+
                    "  }\n" +
                    "}"))
                .andDo(print());;
        verify(productService).create(refEq(productCreateRequestDto));
    }
}
