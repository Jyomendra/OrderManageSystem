package com.ecommerce.ordermanagement.repository;

import com.ecommerce.ordermanagement.entity.Order;
import com.ecommerce.ordermanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
}