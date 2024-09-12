package com.kabarxx.store_example.repositories;

import com.kabarxx.store_example.domain.LikedProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikedProductRepository extends JpaRepository<LikedProduct, Long> {
    List<LikedProduct> findByUserId(Long userId);
    Optional<LikedProduct> findByUserIdAndProductId(Long userId, Long productId);
}
