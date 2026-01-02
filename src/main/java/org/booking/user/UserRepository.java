package org.booking.user;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph()
    Optional<User> findByEmail(String email);

    @EntityGraph()
    Optional<User> findById(Long id);
    Boolean existsByEmail(String email);

}
