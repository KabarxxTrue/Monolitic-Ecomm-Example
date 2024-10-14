package com.kabarxx.store_example.domain.services;

import com.kabarxx.store_example.domain.models.LikedProduct;
import com.kabarxx.store_example.domain.models.Product;
import com.kabarxx.store_example.domain.models.User;
import com.kabarxx.store_example.infrastructure.repositories.ProductRepository;
import com.kabarxx.store_example.infrastructure.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikedProductDomainService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Autowired
    public LikedProductDomainService(UserRepository userRepository, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public LikedProduct likeProduct(User user, Product product) {
        var likedProduct = new LikedProduct();
        likedProduct.setUser(user);
        likedProduct.setProduct(product);

        return likedProduct;
    }

    public LikedProduct likeProduct(Long userId, Long productId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        var product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return createLikedProduct(user, product);
    }

    private LikedProduct createLikedProduct(User user, Product product) {
        LikedProduct likedProduct = new LikedProduct();
        likedProduct.setUser(user);
        likedProduct.setProduct(product);

        return likedProduct;
    }
}
