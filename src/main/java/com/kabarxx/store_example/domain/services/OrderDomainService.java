package com.kabarxx.store_example.domain.services;

import com.kabarxx.store_example.domain.OrderStatus;
import com.kabarxx.store_example.domain.models.Order;
import com.kabarxx.store_example.domain.models.OrderItem;
import com.kabarxx.store_example.domain.models.Product;
import com.kabarxx.store_example.domain.models.User;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderDomainService {

    public BigDecimal calculateTotalPrice(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(OrderItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Order createOrder(User user, List<Product> products) {
        var order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);

        for (Product product : products) {
            OrderItem orderItem = createOrderItem(order, product);
            order.getOrderItems().add(orderItem);
        }

        order.setTotalPrice(calculateTotalPrice(order.getOrderItems()));

        return order;
    }

    public OrderItem createOrderItem(Order order, Product product) {
        var orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setQuantity(1); // TODO
        orderItem.setPrice(product.getPrice());
        orderItem.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity())));

        return orderItem;
    }

    public void cancelOrder(Order order) {
        if (!order.getStatus().equals(OrderStatus.CANCELED))
            order.setStatus(OrderStatus.CANCELED);
        else
            throw new RuntimeException("Order is already canceled.");
    }
}
