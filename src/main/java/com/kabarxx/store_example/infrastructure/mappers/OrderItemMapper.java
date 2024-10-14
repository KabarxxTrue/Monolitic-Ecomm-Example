package com.kabarxx.store_example.infrastructure.mappers;

import com.kabarxx.store_example.domain.models.OrderItem;
import com.kabarxx.store_example.application.dto.OrderItemDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    OrderItem toEntity(OrderItemDTO orderItemDTO);
    OrderItemDTO toDTO(OrderItem orderItem);
}
