package org.booking.beds;

import jakarta.persistence.*;
import lombok.*;
import org.booking.bedTypes.BedType;
import org.booking.rooms.Room;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "beds")
public class Bed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;


    @ManyToOne
    @JoinColumn(name = "bed_type_id")
    private BedType bedType;
}