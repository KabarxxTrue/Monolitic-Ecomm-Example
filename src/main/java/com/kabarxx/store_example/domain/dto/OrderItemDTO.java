package com.kabarxx.store_example.domain.dto;

import java.math.BigDecimal;

public class OrderItemDTO {
    private Long id;
    private Long productId;
    private int quantity;
    private BigDecimal price;
    private BigDecimal totalPrice;
}
