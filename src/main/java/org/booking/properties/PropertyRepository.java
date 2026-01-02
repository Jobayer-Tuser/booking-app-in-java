package org.booking.properties;

import org.booking.apartments.Apartment;
import org.booking.cities.City;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {

    @EntityGraph(attributePaths = {"city"})
    List<Property> searchPropertiesByCity_Id(Long cityId);

    @EntityGraph(attributePaths = {"city"})
    List<Property> findPropertiesByCity_Id(Long aLong);

    @Query(value = "SELECT * FROM properties WHERE city_id = :cityId", nativeQuery = true)
    List<Property> getAllPropertyRelatedTo(@Param("cityId") Long cityId);

    List<Property> findPropertiesByCity_Country_Id(Long cityCountryId);

    List<Property> findPropertiesByApartments(List<Apartment> apartments);

    List<Property> findPropertiesByCityAndApartmentsContains(City city, List<Apartment> apartments);

    // Example of Spring Data Projection
    Optional<PropertyInfo> findAllByCreatedAtOrderByCreatedAtDesc(Instant createdAt);
}
