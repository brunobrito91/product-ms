package com.bruno.abreu.productms.repository;

import com.bruno.abreu.productms.model.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends CrudRepository<Product, UUID> {
    @Override
    List<Product> findAll();

    @Query("select p from Product p " +
            "where (p.name like %:q% or p.description like %:q%) " +
            "and p.price between :minPrice and :maxPrice")
    List<Product> findBySearchParameters(@Param("q") String q, @Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice);

}
