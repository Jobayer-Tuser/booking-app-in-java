package org.booking.Property;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.Fetch;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import lombok.AllArgsConstructor;
import org.booking.Apartment.Apartment;
import org.booking.City.City;
import org.booking.Country.Country;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PropertySpecifications {

    @PersistenceContext
    private final EntityManager entityManager;

    public Specification<Property> withCity(Long cityId) {
        return (property, query, criteriaBuilder) -> {
            if (cityId == null) return null;

            Join<Property, City> city = property.join("city");
            return criteriaBuilder.equal(city.get("id"), cityId);
        };
    }

    public Specification<Property> withCountry(Long countryId) {
        return (property, query, criteriaBuilder) -> {
            if (countryId == null) return null;

            Join<Property, City> city = property.join("city");
            Join<City, Country> country = city.join("country");
            return criteriaBuilder.equal(country.get("id"), countryId);
        };
    }

    public Specification<Property> withCapacity(Integer adults, Integer children) {
        return (property, query, criteriaBuilder) -> {
            if (adults == null && children == null) return null;

            Join<Property, Apartment> apartmentJoin = property.join("apartment");

            query.distinct(true);

            var predicate = criteriaBuilder.conjunction();
            if (adults != null && adults > 0) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.greaterThanOrEqualTo(apartmentJoin.get("capacityAdults"), adults));
            }
            if (children != null && children > 0) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.greaterThanOrEqualTo(apartmentJoin.get("capacityChildren"), children));
            }

            return predicate;
        };
    }
}
