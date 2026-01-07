package org.booking.properties;

import jakarta.persistence.criteria.Join;
import org.booking.apartments.Apartment;
import org.booking.cities.City;
import org.booking.countries.Country;
import org.springframework.data.jpa.domain.Specification;

public class PropertySpecifications {

    public static Specification<Property> withCity(Long cityId) {
        return (root, query, criteriaBuilder) -> {
            if (cityId == null) {
                return null;
            }
            Join<Property, City> cityJoin = root.join("city");
            return criteriaBuilder.equal(cityJoin.get("id"), cityId);
        };
    }

    public static Specification<Property> withCountry(Long countryId) {
        return (root, query, criteriaBuilder) -> {
            if (countryId == null) {
                return null;
            }
            Join<Property, City> cityJoin = root.join("city");
            Join<City, Country> countryJoin = cityJoin.join("country");
            return criteriaBuilder.equal(countryJoin.get("id"), countryId);
        };
    }

    public static Specification<Property> withCapacity(Integer adults, Integer children) {
        return (root, query, criteriaBuilder) -> {
            if (adults == null && children == null) {
                return null;
            }

            Join<Property, Apartment> apartmentJoin = root.join("apartments");

            // Try to select distinct properties to avoid duplicates when multiple
            // apartments match
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
