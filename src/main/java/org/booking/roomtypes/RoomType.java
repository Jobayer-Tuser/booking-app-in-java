package org.booking.roomtypes;

import jakarta.persistence.*;
import lombok.*;
import org.booking.Room.Room;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "room_types")
public class RoomType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    private Instant createdAt;
    private Instant updatedAt;

    @OneToMany(mappedBy = "roomType")
    private List<Room> room = new ArrayList<>();

    public RoomType(String name) { this.name = name; }
}
