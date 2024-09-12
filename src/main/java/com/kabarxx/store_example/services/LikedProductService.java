package com.kabarxx.store_example.services;

import com.kabarxx.store_example.domain.LikedProduct;
import com.kabarxx.store_example.domain.Product;
import com.kabarxx.store_example.domain.User;
import com.kabarxx.store_example.domain.dto.LikedProductDTO;
import com.kabarxx.store_example.mappers.LikedProductMapper;
import com.kabarxx.store_example.repositories.LikedProductRepository;
import com.kabarxx.store_example.repositories.ProductRepository;
import com.kabarxx.store_example.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LikedProductService {
    private final LikedProductRepository likedProductRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final LikedProductMapper likedProductMapper;

    @Autowired
    public LikedProductService(LikedProductRepository likedProductRepository, UserRepository userRepository,
                               ProductRepository productRepository, LikedProductMapper likedProductMapper) {
        this.likedProductRepository = likedProductRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.likedProductMapper = likedProductMapper;
    }

    public List<LikedProductDTO> getLikedProductsById(Long userId) {
        return likedProductRepository.findByUserId(userId).stream()
                .map(likedProductMapper::toDTO)
                .collect(Collectors.toList());
    }

    public LikedProductDTO addLikeProduct(Long userId, Long productId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        LikedProduct likedProduct = new LikedProduct();
        likedProduct.setUser(user);
        likedProduct.setProduct(product);
        likedProductRepository.save(likedProduct);

        return likedProductMapper.toDTO(likedProduct);
    }

    public void removeLikedProduct(Long userId, Long productId) {
        LikedProduct likedProduct = likedProductRepository.findByUserIdAndProductId(userId, productId)
                .orElseThrow(() -> new RuntimeException("Liked product not found"));
        likedProductRepository.delete(likedProduct);
    }
}
