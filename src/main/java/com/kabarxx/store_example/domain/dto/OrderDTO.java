package com.kabarxx.store_example.domain.dto;

import java.math.BigDecimal;
import java.util.List;

public class OrderDTO {
    private Long id;
    private Long userId;
    private List<OrderItemDTO> orderItems;
    private BigDecimal totalPrice;
    private String status;
}
