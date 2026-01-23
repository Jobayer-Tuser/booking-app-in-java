package org.booking.apartmentTypes;

import jakarta.persistence.*;
import lombok.*;
import org.booking.apartments.Apartment;

import java.time.Instant;
import java.util.HashSet;
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