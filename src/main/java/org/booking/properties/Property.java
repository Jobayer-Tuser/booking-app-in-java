package org.booking.properties;

import jakarta.persistence.*;
import jdk.jfr.Unsigned;
import lombok.*;
import org.booking.cities.City;
import org.booking.user.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @PrePersist
    protected void onCreate()
    {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate()
    {
        updatedAt = LocalDateTime.now();
    }
}