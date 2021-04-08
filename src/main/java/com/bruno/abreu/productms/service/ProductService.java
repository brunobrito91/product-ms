package com.bruno.abreu.productms.service;

import com.bruno.abreu.productms.exception.ProductNotFound;
import com.bruno.abreu.productms.model.Product;
import com.bruno.abreu.productms.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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
        findById(product.getId());
        return productRepository.save(product);
    }

    public Product findById(UUID id) {
        return productRepository.findById(id).orElseThrow(ProductNotFound::new);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
