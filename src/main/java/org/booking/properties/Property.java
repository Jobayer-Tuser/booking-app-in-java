package org.booking.properties;

import jakarta.persistence.*;
import lombok.*;
import org.booking.apartments.Apartment;
import org.booking.cities.City;
import org.booking.users.User;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "properties")
@NamedEntityGraph( name = "Property.fullDetails",
    attributeNodes = {
        @NamedAttributeNode(value = "city"),
        @NamedAttributeNode(value = "apartments", subgraph = "apartments-sub")
    },
    subgraphs = {
        @NamedSubgraph(name = "apartments-sub", attributeNodes = {
                @NamedAttributeNode(value = "apartmentType"),
                @NamedAttributeNode(value = "rooms", subgraph = "room-graph")
            }
        ),
        @NamedSubgraph( name = "room-graph", attributeNodes = @NamedAttributeNode(value = "beds", subgraph = "bed-graph")),
        @NamedSubgraph(name = "bed-graph", attributeNodes = @NamedAttributeNode(value = "bedType"))
    }
)
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String addressStreet;
    private String addressPostcode;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Instant createdAt;
    private Instant updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City city;

    @OneToMany(mappedBy = "property", fetch = FetchType.EAGER)
    private Set<Apartment> apartments = new HashSet<>();

    @PrePersist
    protected void onCreate()
    {
        createdAt = Instant.now();
        updatedAt = Instant.now();
    }

    @PreUpdate
    protected void onUpdate()
    {
        updatedAt = Instant.now();
    }
}