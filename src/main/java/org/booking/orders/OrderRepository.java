package org.booking.orders;

import org.booking.users.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @EntityGraph(attributePaths = "items.products")
    @Query("SELECT o FROM Order o WHERE o.customer = :customer")
    List<Order> getAllOrderByCustomer(@Param("customer") User customer);
}