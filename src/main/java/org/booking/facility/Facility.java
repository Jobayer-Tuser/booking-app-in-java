package org.booking.facility;

import jakarta.persistence.*;
import lombok.*;
import org.booking.apartments.Apartment;
import org.booking.facilityCategories.FacilityCategory;
import org.booking.properties.Property;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "facilities")
public class Facility {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "facility_category_id")
    private FacilityCategory category;

    @ManyToMany(mappedBy = "facilities")
    private List<Apartment> apartments = new ArrayList<>();

    @ManyToMany(mappedBy = "facilities")
    private List<Property> properties = new ArrayList<>();

    public Facility(String name) {
        this.name = name;
    }
}