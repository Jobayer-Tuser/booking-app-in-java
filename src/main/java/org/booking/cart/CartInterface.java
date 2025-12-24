package org.booking.cart;

import java.util.UUID;


public interface CartInterface {
    CartDto createNewCart();
    CartDto findCart(UUID cartId);

    CartItemDto addToCart(UUID cartId, Long productId);
    CartItemDto updateCartItem(UUID cartId, Long productId, int quantity);

    Cart deleteCartItem(UUID cartId, Long productId);
    Cart clearCartItem(UUID cartId);
    Cart getCartAndItems(UUID cartId);
}
