package com.kabarxx.store_example.mappers;

import com.kabarxx.store_example.domain.OrderItem;
import com.kabarxx.store_example.domain.dto.OrderItemDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    OrderItem toEntity(OrderItemDTO orderItemDTO);
    OrderItemDTO toDTO(OrderItem orderItem);
}
