package org.booking.Apartment;

import jakarta.persistence.*;
import jdk.jfr.Unsigned;
import lombok.*;
import org.booking.ApartmentTypes.ApartmentType;
import org.booking.Property.Property;
import org.booking.Room.Room;

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
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;

    @ManyToOne
    @JoinColumn(name = "apartment_type_id")
    private ApartmentType apartmentType;

    @OneToMany(mappedBy = "apartment")
    private List<Room> room = new ArrayList<>();

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