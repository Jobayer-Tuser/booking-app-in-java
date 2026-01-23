package org.booking.rooms;

import jakarta.persistence.*;
import lombok.*;
import org.booking.apartments.Apartment;
import org.booking.beds.Bed;
import org.booking.roomtypes.RoomType;

import java.time.Instant;
import java.util.HashSet;
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
    Set<Bed> beds = new HashSet<>();

    private Instant createdAt;
    private Instant updatedAt;

}