package org.booking.util.customquerybuilder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DemoProductRepository extends JpaRepository<DemoProduct, Long>, JpaSpecificationExecutor<DemoProduct> {
}
