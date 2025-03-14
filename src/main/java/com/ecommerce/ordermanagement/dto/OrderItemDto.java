package com.ecommerce.ordermanagement.dto;

import lombok.Data;
import javax.validation.constraints.NotNull;


@Data
public class OrderItemDto {
    @NotNull(message = "Product ID is required")
    private Long productId;

    @NotNull(message = "Quantity is required")
    private Integer quantity;
}