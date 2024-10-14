package com.kabarxx.store_example.infrastructure.mappers;

import com.kabarxx.store_example.domain.models.Cart;
import com.kabarxx.store_example.application.dto.CartDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartMapper {
    CartDTO toDTO(Cart cart);
    Cart toEntity(CartDTO cartDTO);
}
