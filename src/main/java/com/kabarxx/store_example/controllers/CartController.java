package com.kabarxx.store_example.controllers;

import com.kabarxx.store_example.dto.CartDTO;
import com.kabarxx.store_example.dto.CartItemDTO;
import com.kabarxx.store_example.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CartDTO> getUserCartItems(@PathVariable Long userId) {
        CartDTO cart = cartService.getCartByUserId(userId);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/{userId}/add")
    public ResponseEntity<CartDTO> addCartItem(@PathVariable Long userId, @RequestBody CartItemDTO cartItemDTO) {
        CartDTO cart = cartService.addItemToCart(userId, cartItemDTO);
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/{userId}/remove/{productId}")
    public ResponseEntity<CartDTO> removeCartItem(@PathVariable Long userId, @PathVariable Long productId) {
        CartDTO cart = cartService.removeItemFromCart(userId, productId);
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/{userId}/clear")
    public ResponseEntity<CartDTO> clearCart(@PathVariable Long userId) {
        cartService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }
}
