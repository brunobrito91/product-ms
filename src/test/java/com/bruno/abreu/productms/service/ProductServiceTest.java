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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ProductServiceTest {

    private static Product product;
    private static Product expectedNewProduct;
    private static List<Product> products;
    private static List<Product> compatibleProducts;

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
        products = List.of(Product.builder().build(),
                Product.builder().build(),
                Product.builder().build(),
                Product.builder().build(),
                Product.builder().build());
        compatibleProducts = List.of(
                Product.builder()
                        .name("Product 1")
                        .description("Description 1")
                        .price(1.1)
                        .build(),
                Product.builder()
                        .name("Product 2")
                        .description("Description 2")
                        .price(2.2)
                        .build());
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

    @Test
    void findAllProductsShouldReturnAllProducts(){
        when(productRepository.findAll()).thenReturn(products);

        List<Product> productsReturned = productService.findAll();
        assertEquals(products, productsReturned);
    }

    @Test
    void findAllProductsShouldReturnEmptyList(){
        List<Product> emptyList = List.of();
        when(productRepository.findAll()).thenReturn(emptyList);

        List<Product> productsReturned = productService.findAll();
        assertEquals(emptyList, productsReturned);
    }

    @Test
    void findProductsBySearchParametersShouldReturnCompatibleProducts(){
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("q", "Description");
        params.add("min_price", "1");
        params.add("max_price", "3");

        when(productRepository.findBySearchParameters(
                params.getFirst("q"),
                Double.valueOf(Objects.requireNonNull(params.getFirst("min_price"))),
                Double.valueOf(Objects.requireNonNull(params.getFirst("max_price"))))).thenReturn(compatibleProducts);

        List<Product> productsReturned = productService.findBySearchParameters(
                params.getFirst("q"),
                Double.valueOf(Objects.requireNonNull(params.getFirst("min_price"))),
                Double.valueOf(Objects.requireNonNull(params.getFirst("max_price")))
        );
        assertEquals(compatibleProducts, productsReturned);
    }

    @Test
    void findProductsByIncompleteSearchParametersShouldReturnCompatibleProducts(){
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("q", "Description");
        params.add("max_price", "3");

        when(productRepository.findBySearchParameters(
                params.getFirst("q"),
                Double.MIN_VALUE,
                Double.valueOf(Objects.requireNonNull(params.getFirst("max_price"))))).thenReturn(compatibleProducts);

        List<Product> productsReturned = productService.findBySearchParameters(
                params.getFirst("q"),
                Double.MIN_VALUE,
                Double.valueOf(Objects.requireNonNull(params.getFirst("max_price")))
        );
        assertEquals(compatibleProducts, productsReturned);
    }
}
