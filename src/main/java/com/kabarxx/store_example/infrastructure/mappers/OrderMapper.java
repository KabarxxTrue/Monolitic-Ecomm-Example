package com.kabarxx.store_example.infrastructure.mappers;

import com.kabarxx.store_example.domain.models.Order;
import com.kabarxx.store_example.application.dto.OrderDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order toEntity(OrderDTO orderDTO);
    OrderDTO toDTO(Order order);
}
