package org.booking.stores;

import jakarta.persistence.*;
import lombok.*;
import org.booking.users.User;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String street;
    private String zip;
    private String city;
    private String state;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}