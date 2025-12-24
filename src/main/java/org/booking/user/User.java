package org.booking.user;

import jakarta.persistence.*;
import lombok.*;
import org.booking.role.Role;
import org.booking.store.Address;
import org.booking.store.Tag;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String displayName;
    private String email;
    private String password;
    private LocalDateTime emailVerifiedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    @ColumnDefault(value = "1")
    private Role role;

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<Address> addresses = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "user_tags",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();

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

    public void attachAddress(Address address)
    {
        addresses.add(address);
        address.setUser(this);
    }

    public void removeAddress(Address address)
    {
        addresses.remove(address);
        address.setUser(null);
    }

}
