package org.booking.cart;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, UUID> {

    @EntityGraph(attributePaths = "cartItems.product")
    @Query("SELECT cart FROM Cart cart WHERE cart.id = :cartId")
    Optional<Cart> getCartWithItemsAndProduct(@Param("cartId") UUID cartId);
}