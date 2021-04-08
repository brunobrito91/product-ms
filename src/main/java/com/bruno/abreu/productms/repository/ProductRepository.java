package com.bruno.abreu.productms.repository;

import com.bruno.abreu.productms.model.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends CrudRepository<Product, UUID> {
    @Override
    List<Product> findAll();
}
