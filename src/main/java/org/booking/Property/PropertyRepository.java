package org.booking.Property;

import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long>, JpaSpecificationExecutor<Property> {

    @NullMarked
    @EntityGraph(value = "Property.fullDetails", type = EntityGraph.EntityGraphType.LOAD)
    List<Property> findAll(Specification<Property> specification);

    /**
     * Example of Spring Data Projection
     * @param
     * @param type
     * @param <T>
     *
     * Collection<Person> aggregates =people.findByLastname("Matthews", Person.class);
     * Collection<NamesOnly> aggregates = people.findByLastname("Matthews", NamesOnly.class);
     * List<PropertySearchSummary> results = propertyRepository.findBy(spec,PropertySearchSummary.class);
     */
    <T> List<T> findBy(Specification<T> specs, Class<T> type);
    Optional<Property> findAllByCreatedAtOrderByCreatedAtDesc(Instant createdAt);
}
