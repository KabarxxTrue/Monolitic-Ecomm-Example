package com.kabarxx.store_example.services;

import com.kabarxx.store_example.models.Cart;
import com.kabarxx.store_example.models.CartItem;
import com.kabarxx.store_example.models.Product;
import com.kabarxx.store_example.dto.CartDTO;
import com.kabarxx.store_example.dto.CartItemDTO;
import com.kabarxx.store_example.mappers.CartMapper;
import com.kabarxx.store_example.repositories.CartRepository;
import com.kabarxx.store_example.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartMapper cartMapper;

    @Autowired
    public CartService(CartRepository cartRepository, ProductRepository productRepository, CartMapper cartMapper) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.cartMapper = cartMapper;
    }

    public CartDTO getCartByUserId(Long userId) {
        Cart cart = cartRepository.findCartByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user with id: + userId"));
        return cartMapper.toDTO(cart);
    }

    public CartDTO addItemToCart(Long userId, CartItemDTO cartItemDTO) {
        Cart cart = cartRepository.findCartByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user with id: + userId"));

        Product product = productRepository.findById(cartItemDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found for id: + productId"));

        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(cartItemDTO.getQuantity());
        cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(cartItemDTO.getQuantity())));
        cartItem.setCart(cart);

        cart.getItems().add(cartItem);
        cart.setTotalPrice(cart.getTotalPrice().add(cartItem.getPrice()));

        cartRepository.save(cart);

        return cartMapper.toDTO(cart);
    }

    public CartDTO removeItemFromCart(Long userId, Long productId) {
        Cart cart = cartRepository.findCartByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user with userid " + userId));

        Optional<CartItem> cartItemOptional = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (cartItemOptional.isPresent()) {
            CartItem cartItem = cartItemOptional.get();
            cart.setTotalPrice(cart.getTotalPrice().subtract(cartItem.getPrice()));
            cart.getItems().remove(cartItem);
            cartRepository.save(cart);
        }

        return cartMapper.toDTO(cart);
    }

    public void clearCart(Long userId) {
        Cart cart = cartRepository.findCartByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user with id: + userId"));

        cart.getItems().clear();
        cart.setTotalPrice(BigDecimal.ZERO);
        cartRepository.save(cart);
    }
}
