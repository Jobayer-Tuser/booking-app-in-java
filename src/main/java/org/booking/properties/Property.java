package org.booking.properties;

import jakarta.persistence.*;
import jdk.jfr.Unsigned;
import lombok.*;
import org.booking.apartments.Apartment;
import org.booking.cities.City;
import org.booking.user.User;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "properties")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Unsigned
    private Long id;
    private String name;
    private String addressStreet;
    private String addressPostcode;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Instant createdAt;
    private Instant updatedAt;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @OneToMany(mappedBy = "property")
    private List<Apartment> apartments = new ArrayList<>();

    @PrePersist
    protected void onCreate()
    {
        createdAt = Instant.now();
        updatedAt = Instant.now();
    }

    @PreUpdate
    protected void onUpdate()
    {
        updatedAt = Instant.now();
    }
}