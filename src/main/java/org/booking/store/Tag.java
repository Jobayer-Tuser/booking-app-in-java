package org.booking.store;

import jakarta.persistence.*;
import lombok.*;
import org.booking.user.User;

import java.util.HashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tags")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "tags")
    private Set<User> users = new HashSet<>();

}