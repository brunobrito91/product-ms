package com.bruno.abreu.productms.controller;

import com.bruno.abreu.productms.model.Product;
import com.bruno.abreu.productms.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Product> create(@Valid @RequestBody Product product){
        Product newProduct = productService.create(product);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(newProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@Valid @RequestBody Product product, @PathVariable("id") UUID id){
        product.setId(id);
        Product newProduct = productService.update(product);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(newProduct);
    }
}
