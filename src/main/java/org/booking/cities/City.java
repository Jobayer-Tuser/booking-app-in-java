package org.booking.cities;

import jakarta.persistence.*;
import jdk.jfr.Unsigned;
import lombok.*;
import org.booking.countries.Country;
import org.booking.geoobjects.GeoObject;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "cities")
public class City {
    @Id
    @Unsigned
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @OneToMany(mappedBy = "city")
    private List<GeoObject> geoObjects = new ArrayList<>();

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