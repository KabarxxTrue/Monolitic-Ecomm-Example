package com.kabarxx.store_example.repositories;

import com.kabarxx.store_example.domain.LikedProduct;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikedProductRepository extends CrudRepository<LikedProduct, Long> {
}
