package com.kabarxx.store_example.mappers;

import com.kabarxx.store_example.domain.Order;
import com.kabarxx.store_example.domain.dto.OrderDTO;
import org.mapstruct.Mapper;

@Mapper
public interface OrderMapper {
    Order toEntity(OrderDTO orderDTO);
    OrderDTO toDTO(Order order);
}
