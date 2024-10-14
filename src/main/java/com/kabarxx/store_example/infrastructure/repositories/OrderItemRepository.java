package com.kabarxx.store_example.infrastructure.repositories;

import com.kabarxx.store_example.domain.models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
