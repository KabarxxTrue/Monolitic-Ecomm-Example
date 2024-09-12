package com.kabarxx.store_example.controllers;

import com.kabarxx.store_example.domain.dto.LikedProductDTO;
import com.kabarxx.store_example.services.LikedProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/liked-product")
public class LikedProductController {

    private final LikedProductService likedProductService;

    @Autowired
    public LikedProductController(LikedProductService likedProductService) {
        this.likedProductService = likedProductService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<LikedProductDTO>> getLikedProductByUserId(@PathVariable Long userId) {
        List<LikedProductDTO> likedProducts = likedProductService.getLikedProductsById(userId);
        return ResponseEntity.ok(likedProducts);
    }

    @PostMapping("/{userId}/add/{productId}")
    public ResponseEntity<LikedProductDTO> addLikedProduct(@PathVariable Long userId, @PathVariable Long productId) {
        LikedProductDTO likedProduct = likedProductService.addLikeProduct(userId, productId);
        return ResponseEntity.ok(likedProduct);
    }

    @DeleteMapping("/{userId}/remove/{productId}")
    public ResponseEntity<Void> removeLikedProduct(@PathVariable Long userId, @PathVariable Long productId) {
        likedProductService.removeLikedProduct(userId, productId);
        return ResponseEntity.noContent().build();
    }
}
