package com.bruno.abreu.productms.controller;

import com.bruno.abreu.productms.model.Product;
import com.bruno.abreu.productms.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class ProductControllerTest {

    @MockBean
    private ProductService productService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static Product product;

    @BeforeAll
    static void setup(){
        product = Product.builder()
                .id(UUID.randomUUID())
                .name("Product 1")
                .description("Description 1")
                .price(1.0)
                .build();
    }

    @Test
    void saveProductShouldReturnStatusCreated() throws Exception {
        String content = objectMapper.writeValueAsString(product);

        when(productService.save(product)).thenReturn(product);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/products")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void saveProductShouldReturnProductOnResponseBody() throws Exception {
        String content = objectMapper.writeValueAsString(product);

        when(productService.save(product)).thenReturn(product);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/products")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().json(content));
    }

    @Test
    void saveProductIncompleteShouldReturnBadRequest() throws Exception {
        Product productIncomplete = Product.builder().build();
        String content = objectMapper.writeValueAsString(productIncomplete);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/products")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void saveProductIncompleteShouldReturnAnSpecificResponseOnResponseBody() throws Exception {
        Product productIncomplete = Product.builder().build();
        String content = objectMapper.writeValueAsString(productIncomplete);

        String responseBody = "{\"status_code\":400,\"message\":\"[Description must not be blank, Name must not be blank, Price must not be null]\"}";
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/products")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().json(responseBody));
    }

    @Test
    void saveProductWithNegativePriceShouldReturnAnSpecificResponseOnResponseBody() throws Exception {
        Product productIncomplete = Product.builder()
                .price(-1.0)
                .build();
        String content = objectMapper.writeValueAsString(productIncomplete);

        String responseBody = "{\"status_code\":400,\"message\":\"[Description must not be blank, Name must not be blank, Price must be greater than 0]\"}";
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/products")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().json(responseBody));
    }
}
