package org.booking.Room;

import jakarta.persistence.*;
import lombok.*;
import org.booking.Apartment.Apartment;
import org.booking.ApartmentTypes.ApartmentType;
import org.booking.Bed.Bed;
import org.booking.roomtypes.RoomType;

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
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "apartment_id")
    private Apartment apartment;

    @ManyToOne
    @JoinColumn(name = "room_type_id")
    private RoomType roomType;

    @OneToMany(mappedBy = "room")
    Set<Bed> bed = new HashSet<>();

    private Instant createdAt;
    private Instant updatedAt;

}