package org.booking.users;

import jakarta.persistence.*;
import lombok.*;
import org.booking.properties.Property;
import org.booking.roles.Role;
import org.booking.stores.Address;
import org.booking.stores.Tag;
import org.booking.userProfiles.UserProfile;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Builder
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@NamedEntityGraph(name = "graph.userRole", attributeNodes = @NamedAttributeNode("role"))
@EntityListeners(AuditingEntityListener.class)
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

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime updatedAt;

    @CreatedBy
    @Column(updatable = false)
    private Long createdBy;

    @LastModifiedBy
    @Column(insertable = false)
    private Long updatedBy;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Address> addresses = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "user_tags",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();

    @OneToOne(mappedBy = "user")
    private UserProfile profile;

    @OneToMany(mappedBy = "owner")
    private List<Property> properties = new ArrayList<>();

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
