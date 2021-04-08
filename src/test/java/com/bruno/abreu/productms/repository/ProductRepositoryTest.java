package com.bruno.abreu.productms.repository;

import com.bruno.abreu.productms.model.Product;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

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
}
