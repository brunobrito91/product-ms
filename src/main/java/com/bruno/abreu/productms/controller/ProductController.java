package com.bruno.abreu.productms.controller;

import com.bruno.abreu.productms.model.Product;
import com.bruno.abreu.productms.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Product> save(@Valid @RequestBody Product product){
        Product newProduct = productService.save(product);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(newProduct);
    }
}
