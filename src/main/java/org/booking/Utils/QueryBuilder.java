package org.booking.Utils;

import jakarta.persistence.criteria.Path;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class QueryBuilder<T> {

    private Specification<T> specification;
    private final List<Sort.Order> sortOrders = new ArrayList<>();

    public QueryBuilder() {
        this.specification = (root, query, criteriaBuilder) -> criteriaBuilder.conjunction() ;;
    }

    public QueryBuilder<T> where(String field, Object value) {
        return where(field, "=", value);
    }

    public QueryBuilder<T> where(Specification<T> spec) {
        this.specification = this.specification.and(spec);
        return this;
    }

    public QueryBuilder<T> where(String field, String operator, Object value) {
        if (value == null) {
            return this;
        }

        Specification<T> newSpec = (root, query, criteriaBuilder) -> {
            Path<String> path = getPath(root, field);

            return switch (operator.toLowerCase()) {
                case "=" -> criteriaBuilder.equal(path, value);
                case "!=" -> criteriaBuilder.notEqual(path, value);
                case ">" -> criteriaBuilder.greaterThan(path.as(String.class), value.toString());
                case ">=" -> criteriaBuilder.greaterThanOrEqualTo(path.as(String.class), value.toString());
                case "<" -> criteriaBuilder.lessThan(path.as(String.class), value.toString());
                case "<=" -> criteriaBuilder.lessThanOrEqualTo(path.as(String.class), value.toString());
                case "like" -> criteriaBuilder.like(path.as(String.class), value.toString());
                case "containing" -> criteriaBuilder.like(path.as(String.class), "%" + value + "%");
                case "beginning" -> criteriaBuilder.like(path.as(String.class), value + "%");
                case "ending" -> criteriaBuilder.like(path.as(String.class), "%" + value);
                default -> throw new IllegalArgumentException("Unknown operator: " + operator);
            };
        };

        this.specification = this.specification.and(newSpec);
        return this;
    }

    public QueryBuilder<T> orWhere(String field, Object value) {
        return orWhere(field, "=", value);
    }

    public QueryBuilder<T> orWhere(String field, String operator, Object value) {
        if (value == null) {
            return this;
        }
        Specification<T> newSpec = (root, query, criteriaBuilder) -> {
            Path<String> path = getPath(root, field);
            return switch (operator.toLowerCase()) {
                case "=" -> criteriaBuilder.equal(path, value);
                case "!=" -> criteriaBuilder.notEqual(path, value);
                case ">" -> criteriaBuilder.greaterThan(path.as(String.class), value.toString());
                case ">=" -> criteriaBuilder.greaterThanOrEqualTo(path.as(String.class), value.toString());
                case "<" -> criteriaBuilder.lessThan(path.as(String.class), value.toString());
                case "<=" -> criteriaBuilder.lessThanOrEqualTo(path.as(String.class), value.toString());
                case "like" -> criteriaBuilder.like(path.as(String.class), value.toString());
                case "containing" -> criteriaBuilder.like(path.as(String.class), "%" + value + "%");
                default -> throw new IllegalArgumentException("Unknown operator: " + operator);
            };
        };
        this.specification = this.specification.or(newSpec);
        return this;
    }

    public QueryBuilder<T> orderBy(String field, String direction) {
        if ("desc".equalsIgnoreCase(direction)) {
            sortOrders.add(Sort.Order.desc(field));
        } else {
            sortOrders.add(Sort.Order.asc(field));
        }
        return this;
    }

    public List<T> get(JpaSpecificationExecutor<T> repository) {
        if (sortOrders.isEmpty()) {
            return repository.findAll(specification);
        }
        return repository.findAll(specification, Sort.by(sortOrders));
    }

    public Optional<T> first(JpaSpecificationExecutor<T> repository) {
        return repository.findOne(specification);
    }

    public Page<T> paginate(JpaSpecificationExecutor<T> repository, int page, int size) {
        PageRequest pageRequest;
        if (sortOrders.isEmpty()) {
            pageRequest = PageRequest.of(page, size);
        } else {
            pageRequest = PageRequest.of(page, size, Sort.by(sortOrders));
        }
        return repository.findAll(specification, pageRequest);
    }

    private Path<String> getPath(Path<?> root, String field) {
        if (!field.contains(".")) {
            return root.get(field);
        }
        String[] parts = field.split("\\.");
        Path<?> current = root;
        for (String part : parts) {
            current = current.get(part);
        }
        return (Path<String>) current;
    }
}
