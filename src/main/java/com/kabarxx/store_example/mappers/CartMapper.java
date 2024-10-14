package com.kabarxx.store_example.mappers;

import com.kabarxx.store_example.models.Cart;
import com.kabarxx.store_example.dto.CartDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartMapper {
    CartDTO toDTO(Cart cart);
    Cart toEntity(CartDTO cartDTO);
}
