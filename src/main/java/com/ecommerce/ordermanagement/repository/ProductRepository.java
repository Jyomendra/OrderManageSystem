package com.ecommerce.ordermanagement.repository;

import com.ecommerce.ordermanagement.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}