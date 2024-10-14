package com.kabarxx.store_example.application.services;

import com.kabarxx.store_example.domain.models.LikedProduct;
import com.kabarxx.store_example.application.dto.LikedProductDTO;
import com.kabarxx.store_example.domain.services.LikedProductDomainService;
import com.kabarxx.store_example.infrastructure.mappers.LikedProductMapper;
import com.kabarxx.store_example.infrastructure.repositories.LikedProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LikedProductService {

    private final LikedProductRepository likedProductRepository;
    private final LikedProductDomainService likedProductDomainService;
    private final LikedProductMapper likedProductMapper;

    @Autowired
    public LikedProductService(LikedProductRepository likedProductRepository,
                               LikedProductDomainService likedProductDomainService,
                               LikedProductMapper likedProductMapper) {
        this.likedProductRepository = likedProductRepository;
        this.likedProductDomainService = likedProductDomainService;
        this.likedProductMapper = likedProductMapper;
    }

    public LikedProductDTO addLikedProduct(Long userId, Long productId) {
        var likedProduct = likedProductDomainService.likeProduct(userId, productId);

        likedProductRepository.save(likedProduct);

        return likedProductMapper.toDTO(likedProduct);
    }

    public List<LikedProductDTO> getLikedProductsById(Long userId) {
        return likedProductRepository.findByUserId(userId)
                .stream()
                .map(likedProductMapper::toDTO)
                .collect(Collectors.toList());
    }

    public void removeLikedProduct(Long userId, Long productId) {
        var likedProduct = likedProductRepository.findByUserIdAndProductId(userId, productId)
                .orElseThrow(() -> new RuntimeException("Liked product not found"));

        likedProductRepository.delete(likedProduct);
    }
}
