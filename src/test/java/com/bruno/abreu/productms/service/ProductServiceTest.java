package com.bruno.abreu.productms.service;

import com.bruno.abreu.productms.model.Product;
import com.bruno.abreu.productms.repository.ProductRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ProductServiceTest {

    private static Product product;
    private static Product expectedNewProduct;

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @BeforeAll
    static void setup(){
        product = Product.builder()
                .name("Product 1")
                .description("Description 1")
                .price(1.0)
                .build();
        expectedNewProduct = Product.builder()
                .id(UUID.randomUUID())
                .name("Product 1")
                .description("Description 1")
                .price(1.0)
                .build();
    }

    @Test
    void saveProductShouldReturnProduct(){
        when(productRepository.save(product)).thenReturn(expectedNewProduct);

        Product newProduct = productService.create(product);
        assertEquals(expectedNewProduct, newProduct);
    }

    @Test
    void updateProductShouldReturnProduct(){
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(productRepository.save(product)).thenReturn(expectedNewProduct);

        Product newProduct = productService.update(product);
        assertEquals(expectedNewProduct, newProduct);
    }

    @Test
    void findProductByIdShouldReturnProduct(){
        when(productRepository.findById(expectedNewProduct.getId())).thenReturn(Optional.of(expectedNewProduct));

        Product newProduct = productService.findById(expectedNewProduct.getId());
        assertEquals(expectedNewProduct, newProduct);
    }
}
