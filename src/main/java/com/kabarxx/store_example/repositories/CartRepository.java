package com.kabarxx.store_example.repositories;

import com.kabarxx.store_example.domain.Cart;
import com.kabarxx.store_example.domain.dto.CartDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    CartDTO findCartByUserId(Long userId);
}
