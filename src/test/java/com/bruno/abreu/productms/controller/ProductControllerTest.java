package com.bruno.abreu.productms.controller;

import com.bruno.abreu.productms.exception.ProductNotFound;
import com.bruno.abreu.productms.model.Product;
import com.bruno.abreu.productms.service.ProductService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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

    private static List<Product> products;

    @BeforeAll
    static void setup() {
        product = Product.builder()
                .id(UUID.randomUUID())
                .name("Product 1")
                .description("Description 1")
                .price(1.0)
                .build();
        products = List.of(Product.builder().build(),
                Product.builder().build(),
                Product.builder().build(),
                Product.builder().build(),
                Product.builder().build());
    }

    @Test
    void saveProductShouldReturnStatusCreated() throws Exception {
        String content = objectMapper.writeValueAsString(product);

        when(productService.create(product)).thenReturn(product);

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

        when(productService.create(product)).thenReturn(product);

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

    @Test
    void updateProductShouldReturnStatusOk() throws Exception {
        String content = objectMapper.writeValueAsString(product);

        when(productService.update(product)).thenReturn(product);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/products/{id}", product.getId())
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void updateProductShouldReturnProductOnResponseBody() throws Exception {
        String content = objectMapper.writeValueAsString(product);

        when(productService.update(product)).thenReturn(product);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/products/{id}", product.getId())
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().json(content));
    }

    @Test
    void updateProductIncompleteShouldReturnBadRequest() throws Exception {
        Product productIncomplete = Product.builder().build();
        String content = objectMapper.writeValueAsString(productIncomplete);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/products/{id}", product.getId())
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateProductIncompleteShouldReturnAnSpecificResponseOnResponseBody() throws Exception {
        Product productIncomplete = Product.builder().build();
        String content = objectMapper.writeValueAsString(productIncomplete);

        String responseBody = "{\"status_code\":400,\"message\":\"[Description must not be blank, Name must not be blank, Price must not be null]\"}";
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/products/{id}", product.getId())
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().json(responseBody));
    }

    @Test
    void updateProductWithNegativePriceShouldReturnAnSpecificResponseOnResponseBody() throws Exception {
        Product productIncomplete = Product.builder()
                .price(-1.0)
                .build();
        String content = objectMapper.writeValueAsString(productIncomplete);

        String responseBody = "{\"status_code\":400,\"message\":\"[Description must not be blank, Name must not be blank, Price must be greater than 0]\"}";
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/products/{id}", product.getId())
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().json(responseBody));
    }

    @Test
    void updateProductNotSavedYetShouldReturnNotFound() throws Exception {
        String content = objectMapper.writeValueAsString(product);

        when(productService.update(product)).thenThrow(ProductNotFound.class);
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/products/{id}", product.getId())
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void findProductByIdShouldReturnOk() throws Exception {
        UUID id = UUID.randomUUID();
        when(productService.findById(id)).thenReturn(product);
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/products/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void findProductByIdShouldReturnProductOnResponseBody() throws Exception {
        String content = objectMapper.writeValueAsString(product);
        UUID id = UUID.randomUUID();
        when(productService.findById(id)).thenReturn(product);
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/products/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().json(content));
    }

    @Test
    void findProductNotSavedYetByIdShouldReturnNotFound() throws Exception {
        UUID id = UUID.randomUUID();
        when(productService.findById(id)).thenThrow(ProductNotFound.class);
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/products/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void findAllProductsShouldReturnOk() throws Exception {
        when(productService.findAll()).thenReturn(products);
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void findAllProductsShouldReturnAllProductsOnResponseBody() throws Exception {
        String content = objectMapper.writeValueAsString(products);

        when(productService.findAll()).thenReturn(products);
        String contentAsString = mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().json(content))
                .andExpect(jsonPath("$", hasSize(products.size())))
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<Product> productsReturned = objectMapper.readValue(contentAsString, new TypeReference<>() {});
        Assertions.assertEquals(products, productsReturned);
    }

    @Test
    void findAllProductsShouldReturnEmptyListOnResponseBody() throws Exception {
        List<Product> emptyList = List.of();
        String content = objectMapper.writeValueAsString(emptyList);

        when(productService.findAll()).thenReturn(emptyList);
        String contentAsString = mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().json(content))
                .andExpect(jsonPath("$", hasSize(emptyList.size())))
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<Product> productsReturned = objectMapper.readValue(contentAsString, new TypeReference<>() {});
        Assertions.assertEquals(emptyList, productsReturned);
    }
}
