package org.booking.cart;

import jakarta.persistence.*;
import lombok.*;
import org.booking.exception.ResourcesNotFoundException;
import org.booking.product.Product;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    @ColumnDefault("(uuid_to_bin(UUID()))")
    private UUID id;

    @Column(nullable = false)
    @ColumnDefault("(curdate())")
    private LocalDate createdAt;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.MERGE, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<CartItem> cartItems = new HashSet<>();

    @PrePersist
    protected void onCreate()
    {
        createdAt = LocalDate.now();
    }

    public BigDecimal getTotalPrice()
    {
        return cartItems.stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Optional<CartItem> findItemByProductId(Long productId) {
        return cartItems.stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();
    }

    public CartItem addItem(Product product)
    {
        return findItemByProductId(product.getId())
                .map(this::incrementQuantity)
                .orElseGet(() -> createNewCartItems(product));
    }

    public CartItem getItem(Long productId)
    {
        return findItemByProductId(productId)
                .orElseThrow(() -> new ResourcesNotFoundException("Product of ID " + productId + " not found"));
    }

    private CartItem createNewCartItems(Product product) {
        var newItem = CartItem.builder()
                .cart(this)
                .product(product)
                .quantity(1)
                .build();

        cartItems.add(newItem);

        return newItem;
    }

    private CartItem incrementQuantity(CartItem item)
    {
        item.setQuantity(item.getQuantity() + 1);
        return item;
    }

    public void removeItem(Long productId)
    {
        findItemByProductId(productId)
                .ifPresent(cartItem -> {
                    cartItems.remove(cartItem);
                    cartItem.setCart(null);
                });
    }

    public void clear(){
        cartItems.forEach(item -> item.setCart(null));
        cartItems.clear();
    }

    public boolean isEmpty()
    {
        return cartItems.isEmpty();
    }

}