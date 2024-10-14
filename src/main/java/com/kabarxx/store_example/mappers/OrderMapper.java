package com.kabarxx.store_example.mappers;

import com.kabarxx.store_example.models.Order;
import com.kabarxx.store_example.dto.OrderDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order toEntity(OrderDTO orderDTO);
    OrderDTO toDTO(Order order);
}
