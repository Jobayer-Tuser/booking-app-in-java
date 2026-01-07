package org.booking.utils;

import org.springframework.data.jpa.domain.Specification;

import java.util.function.Function;

public class SpecificationBuilder<T> {

    private Specification<T> spec;

    private SpecificationBuilder() {
        this.spec = (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
    }

    public static <T> SpecificationBuilder<T> builder() {
        return new SpecificationBuilder<>();
    }

    public SpecificationBuilder<T> when(boolean condition, Specification<T> specification) {
        if (condition) {
            this.spec = this.spec.and(specification);
        }
        return this;
    }

    public <V> SpecificationBuilder<T> when(V value, Function<V, Specification<T>> specificationFunction) {
        if (value != null) {
            this.spec = this.spec.and(specificationFunction.apply(value));
        }
        return this;
    }

    public Specification<T> build() {
        return this.spec;
    }
}
