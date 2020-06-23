package me.clothesmall.controller.product;

import me.clothesmall.domain.product.dto.IsDeletedEnum;
import me.clothesmall.domain.product.dto.ProductCreateRequestDto;
import me.clothesmall.domain.product.dto.ProductCreateResponseDto;
import me.clothesmall.service.product.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProductService productService;

    @Test
    public void create() throws Exception {
        ProductCreateRequestDto productCreateRequestDto = ProductCreateRequestDto.builder()
                .name("상품이름")
                .costPrice(500)
                .categoryDetail(1L)
                .sellingPrice(500)
                .productInformation("상품정보")
                .build();

        ProductCreateResponseDto productCreateResponseDto = ProductCreateResponseDto.builder()
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

        given(productService.create(productCreateRequestDto)).will(invocation -> productCreateResponseDto);

//        mockMvc.perform(post("/api/products")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content("{\n" +
//                    "        \"name\" : \"상품이름\",\n" +
//                    "        \"cost_price\" : 500,\n" +
//                    "        \"category\" : 1,\n" +
//                    "        \"category_detail\" : 1,\n" +
//                    "        \"selling_price\" : 500,\n" +
//                    "        \"product_information\" : \"상품정보\"\n" +
//                    "}"))
//                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\n" +
                    "        \"name\" : \"상품이름\",\n" +
                    "        \"cost_price\" : 500,\n" +
                    "        \"category\" : 1,\n" +
                    "        \"category_detail\" : 1,\n" +
                    "        \"selling_price\" : 500,\n" +
                    "        \"product_information\" : \"상품정보\"\n" +
                    "}"))
            .andExpect(content().string("{{\n" +
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
                    "}}"));

        verify(productService.create(productCreateRequestDto));
    }
}
