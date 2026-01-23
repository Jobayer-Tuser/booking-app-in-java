package org.booking.properties;

import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long>, JpaSpecificationExecutor<Property> {

    @NullMarked
    @EntityGraph(value = "properties.fullDetails", type = EntityGraph.EntityGraphType.LOAD)
    List<Property> findAll(Specification<Property> specification);

    @Query("SELECT p FROM Property p JOIN p.apartments a WHERE p.id = :id ORDER BY a.capacityAdults DESC ")
    Optional<Property> findPropertyWithSortedApartments(@Param("id") Long id);
}
