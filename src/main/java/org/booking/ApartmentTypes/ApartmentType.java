package org.booking.ApartmentTypes;

import jakarta.persistence.*;
import lombok.*;
import org.booking.Apartment.Apartment;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "apartment_types")
public class ApartmentType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Instant createdAt;
    private Instant updatedAt;

    public ApartmentType(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "apartmentType")
    Set<Apartment> apartments = new HashSet<>();
}