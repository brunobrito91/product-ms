package com.bruno.abreu.productms.repository;

import com.bruno.abreu.productms.model.Product;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductRepositoryTest {

    private static Product product;

    @Autowired
    private ProductRepository productRepository;

    @BeforeAll
    static void setup(){
        product = Product.builder()
                .name("Product 1")
                .description("Description 1")
                .price(1.0)
                .build();
    }

    @Test
    void saveProductShouldReturnProductWithId(){
        Product newProduct = productRepository.save(product);
        assertTrue(productRepository.findById(newProduct.getId()).isPresent());
    }

    @Test
    void updateProductShouldReturnProductWithId(){
        product.setId(UUID.fromString("fe13fded-fa56-425b-bc35-ae70981dcfb8"));
        productRepository.findById(product.getId()).orElseThrow();
        Product newProduct = productRepository.save(product);
        assertEquals(product.getId(),newProduct.getId());
        assertTrue(productRepository.findById(newProduct.getId()).isPresent());
    }

    @Test
    void findProductByIdShouldBeFound(){
        UUID id = UUID.fromString("fe13fded-fa56-425b-bc35-ae70981dcfb8");
        assertTrue(productRepository.findById(id).isPresent());
    }

    @Test
    void findProductNotSavedYetByIdShouldNotBeFound(){
        UUID id = UUID.randomUUID();
        assertFalse(productRepository.findById(id).isPresent());
    }

    @Test
    void findAllProductsShouldReturnAllProducts(){
        assertFalse(productRepository.findAll().isEmpty());
        assertEquals(5, productRepository.findAll().size());
    }

//    @Test
//    @Sql("/product-table-empty.sql")
//    @Sql(scripts = "/data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
//    void findAllProductShouldReturnEmptyList(){
//        assertTrue(productRepository.findAll().isEmpty());
//    }
}
