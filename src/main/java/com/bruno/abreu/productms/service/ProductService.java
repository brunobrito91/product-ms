package com.bruno.abreu.productms.service;

import com.bruno.abreu.productms.exception.ProductNotFound;
import com.bruno.abreu.productms.model.Product;
import com.bruno.abreu.productms.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product create(Product product) {
        return productRepository.save(product);
    }

    public Product update(Product product) {
        productRepository.findById(product.getId()).orElseThrow(ProductNotFound::new);
        return productRepository.save(product);
    }
}
