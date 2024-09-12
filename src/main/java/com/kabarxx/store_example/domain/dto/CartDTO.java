package com.kabarxx.store_example.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class CartDTO {
    private Long id;
    private List<CartItemDTO> items;
    private BigDecimal totalPrice;
}
