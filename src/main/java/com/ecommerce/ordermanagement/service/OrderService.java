package com.ecommerce.ordermanagement.service;

import com.ecommerce.ordermanagement.dto.OrderRequestDto;
import com.ecommerce.ordermanagement.dto.OrderItemDto;
import com.ecommerce.ordermanagement.entity.*;
import com.ecommerce.ordermanagement.exception.InsufficientStockException;
import com.ecommerce.ordermanagement.repository.OrderRepository;
import com.ecommerce.ordermanagement.repository.ProductRepository;
import com.ecommerce.ordermanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private PaymentService paymentService;

    @Transactional
    public Order createOrder(OrderRequestDto orderRequest) {
        User user = userRepository.findById(orderRequest.getUserId())
            .orElseThrow(() -> new RuntimeException("User not found"));

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (OrderItemDto itemDto : orderRequest.getItems()) {
            Product product = productRepository.findById(itemDto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

            if (product.getStockQuantity() < itemDto.getQuantity()) {
                throw new InsufficientStockException(
                    "Insufficient stock for product: " + product.getName());
            }

            // Update stock
            product.setStockQuantity(product.getStockQuantity() - itemDto.getQuantity());
            productRepository.save(product);

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(itemDto.getQuantity());
            orderItem.setPrice(product.getPrice());
            orderItems.add(orderItem);

            totalAmount = totalAmount.add(product.getPrice()
                .multiply(BigDecimal.valueOf(itemDto.getQuantity())));
        }

        // Create payment intent
        String paymentIntentId = paymentService.createPaymentIntent(totalAmount);

        Order order = new Order();
        order.setUser(user);
        order.setOrderItems(orderItems);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);
        order.setTotalAmount(totalAmount);
        order.setPaymentIntentId(paymentIntentId);

        orderItems.forEach(item -> item.setOrder(order));
        
        return orderRepository.save(order);
    }

    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public List<Order> getUserOrders(User user) {
        return orderRepository.findByUser(user);
    }

    @Transactional
    public Order updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = getOrder(orderId);
        order.setStatus(status);
        return orderRepository.save(order);
    }
}