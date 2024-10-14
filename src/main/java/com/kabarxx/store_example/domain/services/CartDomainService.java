package com.kabarxx.store_example.domain.services;

import com.kabarxx.store_example.domain.models.Cart;
import com.kabarxx.store_example.domain.models.CartItem;
import com.kabarxx.store_example.domain.models.Product;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class CartDomainService {

    public void addItemToCart(Cart cart, Product product, int quantity) {
        var cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
        cartItem.setCart(cart);

        cart.getItems().add(cartItem);
        cart.setTotalPrice(cart.getTotalPrice().add(cartItem.getPrice()));
    }

    public void removeItemFromCart(Cart cart, Product product) {
        Optional<CartItem> cartItemOptional = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst();

        if (cartItemOptional.isPresent()) {
            CartItem cartItem = cartItemOptional.get();
            cart.setTotalPrice(cart.getTotalPrice().subtract(cartItem.getPrice()));
            cart.getItems().remove(cartItem);
        } else
            throw new RuntimeException("Item not found in cart");
    }

    public void clearCart(Cart cart) {
        cart.getItems().clear();
        cart.setTotalPrice(BigDecimal.ZERO);
    }
}
