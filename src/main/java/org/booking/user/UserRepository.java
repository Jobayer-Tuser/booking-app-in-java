package org.booking.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);

    List<User> findByName(String name);
    List<User> findByNameLike(String name);
    List<User> findByNameNotLike(String name);
    List<User> findByNameContaining(String name);
    List<User> findByNameStartingWith(String name);
    List<User> findByNameStartingWithIgnoreCase(String name);
    List<User> findByNameEndingWith(String name);
    List<User> findByNameEndingWithIgnoreCase(String name);

}
