package org.booking.cities;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    @Override
    @EntityGraph(attributePaths = {})
    Optional<City> findById(Long id);
}
