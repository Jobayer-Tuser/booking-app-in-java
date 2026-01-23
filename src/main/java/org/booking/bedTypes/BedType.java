package org.booking.bedTypes;

import jakarta.persistence.*;
import lombok.*;
import org.booking.beds.Bed;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "bed_types")
public class BedType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "bedType")
    private List<Bed> bed = new ArrayList<>();

    public BedType(String name) {
        this.name = name;
    }
}