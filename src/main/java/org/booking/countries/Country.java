package org.booking.countries;

import jakarta.persistence.*;
import jdk.jfr.Unsigned;
import lombok.*;
import org.booking.cities.City;
import org.booking.userprofiles.UserProfile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "countries")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Unsigned
    private Long id;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToOne(mappedBy = "invoiceCountryId")
    private UserProfile countryProfile;

    @OneToOne(mappedBy = "nationalityCountryId")
    private UserProfile nationalityProfile;

    @OneToMany(mappedBy = "country")
    private List<City> cities = new ArrayList<>();

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