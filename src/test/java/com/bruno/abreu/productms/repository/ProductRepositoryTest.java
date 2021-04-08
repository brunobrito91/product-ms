package com.bruno.abreu.productms.repository;

import com.bruno.abreu.productms.model.Product;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Objects;
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

    @Test
    void findProductsBySearchParametersShouldReturnCompatibleProducts(){
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("q", "Description");
        params.add("min_price", "1");
        params.add("max_price", "3");

        List<Product> compatibleProducts = productRepository.findBySearchParameters(
                params.getFirst("q"),
                Double.valueOf(Objects.requireNonNull(params.getFirst("min_price"))),
                Double.valueOf(Objects.requireNonNull(params.getFirst("max_price"))));

        assertFalse(compatibleProducts.isEmpty());
        assertEquals(2, compatibleProducts.size());
        assertEquals("fe13fded-fa56-425b-bc35-ae70981dcfb8", compatibleProducts.get(0).getId().toString());
        assertEquals("97d769a2-e303-4dbf-933a-cb6447a33aab", compatibleProducts.get(1).getId().toString());
    }

    @Test
    void findProductsBySearchParametersWithouMinPriceShouldReturnCompatibleProducts(){
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("q", "Description");
        params.add("max_price", "3");

        List<Product> compatibleProducts = productRepository.findBySearchParameters(
                params.getFirst("q"),
                Double.MIN_VALUE,
                Double.valueOf(Objects.requireNonNull(params.getFirst("max_price"))));

        assertFalse(compatibleProducts.isEmpty());
        assertEquals(2, compatibleProducts.size());
    }

    @Test
    void findProductsBySearchParametersWithouMaxPriceShouldReturnCompatibleProducts(){
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("q", "Description");
        params.add("min_price", "1");

        List<Product> compatibleProducts = productRepository.findBySearchParameters(
                params.getFirst("q"),
                Double.valueOf(Objects.requireNonNull(params.getFirst("min_price"))),
                Double.MAX_VALUE);

        assertFalse(compatibleProducts.isEmpty());
        assertEquals(5, compatibleProducts.size());
    }

    @Test
    @Sql("/create-product-to-be-deleted.sql")
    void deleteProductByIdShouldWorkFine(){
        UUID id = UUID.fromString("788d791e-6c2e-44e3-925c-e6cee648df26");
        assertTrue(productRepository.findById(id).isPresent());
        productRepository.deleteById(id);
        assertFalse(productRepository.findById(id).isPresent());
    }

}
