package org.booking.properties;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.Join;
import lombok.AllArgsConstructor;
import org.booking.apartments.Apartment;
import org.booking.cities.City;
import org.booking.country.Country;
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
        return (property, query, cb) -> {
            if (adults == null && children == null) return null;

            Join<Property, Apartment> apartmentJoin = property.join("apartments");

            query.distinct(true);

            var predicate = cb.conjunction();
            if (adults != null && adults > 0) {
                predicate = cb.or(predicate, cb.greaterThanOrEqualTo(apartmentJoin.get("capacityAdults"), adults));
            }
            if (children != null && children > 0) {
                predicate = cb.or(predicate, cb.greaterThanOrEqualTo(apartmentJoin.get("capacityChildren"), children));
            }

            return predicate;
        };
    }

    public Specification<Property> orderByCapacity() {
        return (property, query, builder ) -> {
            Join<Property, Apartment> apartment = property.join("apartments");
            query.orderBy(
                    builder.desc(apartment.get("capacityAdults")),
                    builder.desc(apartment.get("capacityChildren"))
            );
            return builder.conjunction();
        };
    }

    public Specification<Property> findPropertyById(Long propertyId) {
        return (property, query, builder ) -> {
            if (propertyId == null) return null;

            return builder.equal(property.get("id"), propertyId);
        };
    }
}
