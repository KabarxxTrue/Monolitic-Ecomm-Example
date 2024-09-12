package com.kabarxx.store_example.services;

import com.kabarxx.store_example.domain.Order;
import com.kabarxx.store_example.domain.OrderItem;
import com.kabarxx.store_example.domain.Product;
import com.kabarxx.store_example.domain.User;
import com.kabarxx.store_example.domain.dto.OrderDTO;
import com.kabarxx.store_example.domain.enumerations.OrderStatus;
import com.kabarxx.store_example.mappers.OrderMapper;
import com.kabarxx.store_example.repositories.OrderItemRepository;
import com.kabarxx.store_example.repositories.OrderRepository;
import com.kabarxx.store_example.repositories.ProductRepository;
import com.kabarxx.store_example.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderMapper orderMapper;

    @Autowired
    public OrderService(OrderRepository orderRepository, UserRepository userRepository,
                        ProductRepository productRepository, OrderItemRepository orderItemRepository,
                        OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.orderItemRepository = orderItemRepository;
        this.orderMapper = orderMapper;
    }

    public List<OrderDTO> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId)
                .stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());
    }

    public OrderDTO createOrder(Long userId, List<Long> productIds) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Product> products = productRepository.findAllById(productIds);
        if (products.isEmpty())
            throw new RuntimeException("Product not found");

        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);

        BigDecimal totalPrice = BigDecimal.ZERO;

        for (Product product : products) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(1);
            orderItem.setPrice(product.getPrice());
            orderItem.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity())));
            totalPrice = totalPrice.add(orderItem.getTotalPrice());

            order.getOrderItems().add(orderItem);
        }

        order.setTotalPrice(totalPrice);
        orderRepository.save(order);

        return orderMapper.toDTO(order);
    }

    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(OrderStatus.CANCELED);
        orderRepository.save(order);
    }
}
