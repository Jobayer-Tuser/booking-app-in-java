package org.booking.apartments;

import jakarta.persistence.*;
import jdk.jfr.Unsigned;
import lombok.*;
import org.booking.apartmentTypes.ApartmentType;
import org.booking.facility.Facility;
import org.booking.properties.Property;
import org.booking.rooms.Room;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "apartments")
public class Apartment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Unsigned
    private Long id;

    private String name;
    private int capacityAdults;
    private int capacityChildren;
    private Integer bathroom;
    private Integer size;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;

    @ManyToOne
    @JoinColumn(name = "apartment_type_id")
    private ApartmentType apartmentType;

    @OneToMany(mappedBy = "apartment")
    private List<Room> rooms = new ArrayList<>();

    @ManyToMany(mappedBy = "apartments")
    private List<Facility> facilities = new ArrayList<>();

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