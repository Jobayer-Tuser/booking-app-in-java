package org.booking.store;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "profiles")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @Column(length = 15)
    private String phoneNumber;
    private LocalDate dateOfBirth;

    @Column(columnDefinition = "INT UNSIGNED DEFAULT 0")
    private int loyaltyPoints = 0;

}