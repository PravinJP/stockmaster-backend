package com.project.stockmaster.repository;


import com.project.stockmaster.Models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByProductCode(String productCode);
}
