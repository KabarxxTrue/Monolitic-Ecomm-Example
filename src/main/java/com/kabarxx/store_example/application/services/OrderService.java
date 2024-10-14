package com.kabarxx.store_example.application.services;

import com.kabarxx.store_example.application.dto.OrderDTO;
import com.kabarxx.store_example.domain.services.OrderDomainService;
import com.kabarxx.store_example.infrastructure.mappers.OrderMapper;
import com.kabarxx.store_example.infrastructure.repositories.OrderRepository;
import com.kabarxx.store_example.infrastructure.repositories.ProductRepository;
import com.kabarxx.store_example.infrastructure.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderDomainService orderDomainService;
    private final OrderMapper orderMapper;

    @Autowired
    public OrderService(OrderRepository orderRepository, UserRepository userRepository,
                        ProductRepository productRepository, OrderDomainService orderDomainService,
                        OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.orderDomainService = orderDomainService;
        this.orderMapper = orderMapper;
    }

    public List<OrderDTO> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId)
                .stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());
    }

    public OrderDTO createOrder(Long userId, List<Long> productIds) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        var products = productRepository.findAllById(productIds);
        if (products.isEmpty())
            throw new RuntimeException("No products found");

        var order = orderDomainService.createOrder(user, products);

        orderRepository.save(order);

        return orderMapper.toDTO(order);
    }

    public void cancelOrder(Long orderId) {
        var order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        orderDomainService.cancelOrder(order);

        orderRepository.save(order);
    }
}
