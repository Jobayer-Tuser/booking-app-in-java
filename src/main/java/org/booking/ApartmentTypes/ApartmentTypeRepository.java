package org.booking.ApartmentTypes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApartmentTypeRepository extends JpaRepository<ApartmentType, Long> {

}
