package org.booking.Utils;

import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.util.function.Function;

public class SpecificationBuilder<T> {

    private Specification<T> spec;

    public SpecificationBuilder() {
        this.spec = (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
    }

    public SpecificationBuilder<T> when(boolean condition, Specification<T> specification) {
        if (condition) {
            this.spec = this.spec.and(specification);
        }
        return this;
    }

    public <V> SpecificationBuilder<T> whereId(V value, Function<V, Specification<T>> specificationFunction) {
        if (value != null) {
            this.spec = this.spec.and(specificationFunction.apply(value));
        }
        return this;
    }

    public <V> SpecificationBuilder<T> when(V value, Function<V, Specification<T>> specificationFunction) {
        if (value != null) {
            this.spec = this.spec.and(specificationFunction.apply(value));
        }
        return this;
    }

    public SpecificationBuilder<T> load(String entity) {
        this.spec = this.spec.and((root, query, criteriaBuilder) -> {
            if (Long.class != query.getResultType() && long.class != query.getResultType()) {
                root.fetch(entity, JoinType.LEFT);
            }
            return criteriaBuilder.conjunction();
        });
        return this;
    }

    public SpecificationBuilder<T> load(Class<?> entityClz) {
        this.spec = this.spec.and((root, query, criteriaBuilder) -> {
            if (Long.class != query.getResultType() && long.class != query.getResultType()) {
                var attribute = root.getModel().getAttributes().stream()
                        .filter(a -> a.getJavaType().equals(entityClz))
                        .findFirst();
                if (attribute.isPresent()) {
                    root.fetch(attribute.get().getName(), JoinType.LEFT);
                }
            }
            return criteriaBuilder.conjunction();
        });
        return this;
    }

    public Specification<T> build() {
        return this.spec;
    }
}
