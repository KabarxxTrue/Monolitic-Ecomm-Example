package com.kabarxx.store_example.mappers;

import com.kabarxx.store_example.domain.Cart;
import com.kabarxx.store_example.domain.dto.CartDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartMapper {
    CartDTO toDTO(Cart cart);
    Cart toEntity(CartDTO cartDTO);
}
