package org.booking.facilityCategories;

import jakarta.persistence.*;
import lombok.*;
import org.booking.facility.Facility;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "facility_categories")
public class FacilityCategory {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Facility> facilities = new ArrayList<>();

    public FacilityCategory(String name) {
        this.name = name;
    }
}