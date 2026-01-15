package org.booking.Property;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.booking.City.City;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PropertyCriteriaRepositoryImplements implements PropertyCriteriaRepository{

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public List<Property> findPropertyByCritearia(String city, String capacity, String country) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Property> criteriaQuery = criteriaBuilder.createQuery(Property.class);
        Root<Property> root = criteriaQuery.from(Property.class);

        Join<Property, City> property = root.join(City.class);

        List<Predicate> predicates = new ArrayList<>();

        if (city != null) {
            predicates.add(criteriaBuilder.equal(root.get("city"), city));
        }

        if (country != null) {
//            predicates.add(criteriaBuilder.eq);
        }

        criteriaQuery.select(root).where(predicates.toArray(new Predicate[predicates.size()]));

        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
