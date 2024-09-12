package com.kabarxx.store_example.repositories;

import com.kabarxx.store_example.domain.User;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository {
    User findByUserId(Long userId);
}
