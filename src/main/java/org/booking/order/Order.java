package org.booking.order;

import jakarta.persistence.*;
import lombok.*;
import org.booking.cart.Cart;
import org.booking.user.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User customer;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private BigDecimal totalPrice;

    @Column(insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "order", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Set<OrderItem> items = new HashSet<>();

    @PrePersist
    protected void onCreate()
    {
        createdAt = LocalDateTime.now();
    }

    public static Order fromCart(Cart cart, User customer)
    {
        var order = Order.builder()
                .customer(customer)
                .status(OrderStatus.Pending)
                .totalPrice(cart.getTotalPrice())
                .build();

        cart.getCartItems().forEach(cartItem -> {
            var orderItem = OrderItem.builder()
                    .order(order)
                    .product(cartItem.getProduct())
                    .quantity(cartItem.getQuantity())
                    .unitPrice(cartItem.getProduct().getPrice() )
                    .totalPrice(cartItem.getTotalPrice())
                    .build();

            order.addItem(orderItem);
        });

        return order;
    }

    public void addItem(OrderItem item) {
        if (this.items == null) {
            this.items = new HashSet<>();
        }
        this.items.add(item);
        item.setOrder(this); // Keeps the bidirectional link in sync
    }
}