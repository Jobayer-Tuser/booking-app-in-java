package org.booking.products;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Override
    @EntityGraph(attributePaths = {"category"})
    List<Product> findAll();

    @Query("select p.id, p.name, p.price from Product p")
    <T> List <T> getAllProducts(Class<T> type);
}