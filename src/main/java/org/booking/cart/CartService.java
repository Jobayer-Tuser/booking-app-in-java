package org.booking.cart;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.booking.exception.ResourcesNotFoundException;
import org.booking.product.ProductInterface;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
class CartService implements CartInterface {

    private final CartRepository cartRepository;
    private final ProductInterface productInterface;
    private final CartMapper cartMapper;

    @Override
    public CartDto createNewCart() {

        Cart cart = new Cart();
        cartRepository.save(cart);

        return cartMapper.toDto(cart);
    }

    @Override
    public CartDto findCart(UUID cartId)
    {
        var cart = getCartAndItems(cartId);
        return cartMapper.toDto(cart);
    }

    @Override
    @Transactional
    public CartItemDto addToCart(UUID cartId, Long productId)
    {
        var product = productInterface.findProductById(productId);
        var cart = getCartAndItems(cartId);
        var cartItem = cart.addItem(product);

        cartRepository.save(cart);

        return cartMapper.toItemDto(cartItem);
    }

    @Override
    @Transactional
    public CartItemDto updateCartItem(UUID cartId, Long productId, int quantity)
    {
        var cart = getCartAndItems(cartId);
        var cartItem = cart.getItem(productId);

        cartItem.setQuantity(quantity);
        cartRepository.save(cart);

        return cartMapper.toItemDto(cartItem);
    }

    @Override
    public Cart deleteCartItem(UUID cartId, Long productId)
    {
        var cart = getCartAndItems(cartId);
        cart.removeItem(productId);
        return cartRepository.save(cart);
    }

    @Override
    public Cart clearCartItem(UUID cartId)
    {
        var cart = getCartAndItems(cartId);
        cart.clear();
        return cartRepository.save(cart);
    }

    @Override
    public Cart getCartAndItems(UUID cartId){
        return cartRepository.getCartWithItemsAndProduct(cartId)
                .orElseThrow(() -> new ResourcesNotFoundException("Cart ID" + cartId + " does not exists!"));
    }
}
