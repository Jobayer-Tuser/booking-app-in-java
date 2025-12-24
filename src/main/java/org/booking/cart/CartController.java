package org.booking.cart;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.booking.auth.AuthService;
import org.booking.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.UUID;

@Tag(name ="Carts")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/carts")
public class CartController {

    private final CartInterface cartInterface;
    private final AuthService authService;

    @GetMapping("/{cartId}")
    public ResponseEntity<CartDto> getCart(@PathVariable UUID cartId)
    {
        var cartDto = cartInterface.findCart(cartId);
        return ResponseEntity.ok(cartDto);
    }

    @PostMapping
    public ResponseEntity<CartDto> createCart(UriComponentsBuilder uriBuilder)
    {

        var cartDto = cartInterface.createNewCart();
        var uri = uriBuilder.path("/api/carts/{id}").buildAndExpand(cartDto.id()).toUri();

        return ResponseEntity.created(uri).body(cartDto);
    }

    @PostMapping("/{cartId}/items")
    public ResponseEntity<CartItemDto> addItemToCart(@PathVariable UUID cartId, @RequestBody AddToCartItemRequest request)
    {
        var cartItemDto = cartInterface.addToCart(cartId, request.productId());

        return ResponseEntity.status(HttpStatus.CREATED).body(cartItemDto);
    }

    @PatchMapping("/{cartId}/items/{productId}")
    public ResponseEntity<CartItemDto> updateCartItem(@PathVariable UUID cartId, @PathVariable Long productId, @Valid  @RequestBody UpdateCartItemRequest request)
    {
        var cartItemDto = cartInterface.updateCartItem(cartId, productId, request.quantity());
        return ResponseEntity.ok(cartItemDto);
    }

    @DeleteMapping("/{cartId}/items/{productId}")
    public ResponseEntity<?> deleteCartItem(@PathVariable UUID cartId, @PathVariable Long productId){
        var isDeleted = cartInterface.deleteCartItem(cartId, productId);
        if (isDeleted != null) {
            return ResponseEntity.ok(
                    Map.of("success", "Product deleted successfully!")
            );
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{cartId}/items")
    public ResponseEntity<?> clearCartItem(@PathVariable UUID cartId){
        var isCleared = cartInterface.clearCartItem(cartId);
        if (isCleared != null) {
            return ResponseEntity.ok(
                    Map.of("success", "Cart item cleared successfully!")
            );
        }
        return ResponseEntity.noContent().build();
    }
}
