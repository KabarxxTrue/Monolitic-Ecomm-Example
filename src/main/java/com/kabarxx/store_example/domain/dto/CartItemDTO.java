package com.kabarxx.store_example.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemDTO {
    private Long productId;
    private int quantity;
}
