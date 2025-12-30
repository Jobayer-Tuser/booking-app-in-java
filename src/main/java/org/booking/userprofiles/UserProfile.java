package org.booking.userprofiles;

import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import jdk.jfr.Unsigned;
import lombok.*;
import org.booking.countries.Country;
import org.booking.user.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user_profiles")
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Unsigned
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(value = EnumType.STRING)
    private Gender gender;
    private LocalDate birthDate;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String invoiceAddress;
    private String invoicePostCode;
    private String invoiceCity;

    @OneToOne
    @JoinColumn(name = "invoice_country_id")
    private Country invoiceCountryId;

    @OneToOne
    @JoinColumn(name = "nationality_country_id")
    private Country nationalityCountryId;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

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