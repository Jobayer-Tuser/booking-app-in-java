package org.booking.store;

import jakarta.persistence.*;
import lombok.*;
import org.booking.product.Product;

import java.util.HashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Byte id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "category")
    private Set<Product> products = new HashSet<>();

}