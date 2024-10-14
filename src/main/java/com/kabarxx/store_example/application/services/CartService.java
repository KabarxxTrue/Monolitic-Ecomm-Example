package com.kabarxx.store_example.application.services;

import com.kabarxx.store_example.application.dto.CartDTO;
import com.kabarxx.store_example.application.dto.CartItemDTO;
import com.kabarxx.store_example.domain.services.CartDomainService;
import com.kabarxx.store_example.infrastructure.mappers.CartMapper;
import com.kabarxx.store_example.infrastructure.repositories.CartRepository;
import com.kabarxx.store_example.infrastructure.repositories.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartMapper cartMapper;
    private final CartDomainService cartDomainService;

    public CartService(CartRepository cartRepository, ProductRepository productRepository, CartMapper cartMapper, CartDomainService cartDomainService) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.cartMapper = cartMapper;
        this.cartDomainService = cartDomainService;
    }

    public CartDTO getCartByUserId(Long userId) {
        var cart = cartRepository.findCartByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user with id: " + userId));

        return cartMapper.toDTO(cart);
    }

    public CartDTO addItemToCart(Long userId, CartItemDTO cartItemDTO) {
        var cart = cartRepository.findCartByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user with id: " + userId));

        var product = productRepository.findById(cartItemDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found for id: " + cartItemDTO.getProductId()));

        cartDomainService.addItemToCart(cart, product, cartItemDTO.getQuantity());

        cartRepository.save(cart);

        return cartMapper.toDTO(cart);
    }

    public CartDTO removeItemFromCart(Long userId, Long productId) {
        var cart = cartRepository.findCartByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user with userid " + userId));

        var product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found for id: " + productId));

        cartDomainService.removeItemFromCart(cart, product);

        cartRepository.save(cart);

        return cartMapper.toDTO(cart);
    }

    public void clearCart(Long userId) {
        var cart = cartRepository.findCartByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user with id: " + userId));

        cartDomainService.clearCart(cart);

        cartRepository.save(cart);
    }
}
