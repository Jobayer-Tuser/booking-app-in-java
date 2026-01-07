package org.booking.utils.customquerybuilder;

import jakarta.persistence.criteria.Path;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EloquentQueryBuilder<T> {

    private final Class<T> entityClass;
    private Specification<T> specification;
    private final List<Sort.Order> sortOrders = new ArrayList<>();

    public EloquentQueryBuilder(Class<T> entityClass) {
        this.entityClass = entityClass;
        this.specification = Specification.where(null);
    }

    public EloquentQueryBuilder<T> where(String field, Object value) {
        return where(field, "=", value);
    }

    public EloquentQueryBuilder<T> where(Specification<T> spec) {
        this.specification = this.specification.and(spec);
        return this;
    }

    public EloquentQueryBuilder<T> where(String field, String operator, Object value) {
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

    public EloquentQueryBuilder<T> orWhere(String field, Object value) {
        return orWhere(field, "=", value);
    }

    public EloquentQueryBuilder<T> orWhere(String field, String operator, Object value) {
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

    public EloquentQueryBuilder<T> orderBy(String field, String direction) {
        if ("desc".equalsIgnoreCase(direction)) {
            sortOrders.add(Sort.Order.desc(field));
        } else {
            sortOrders.add(Sort.Order.asc(field));
        }
        return this;
    }

    private final List<String> selectedColumns = new ArrayList<>();

    public EloquentQueryBuilder<T> select(String... fields) {
        if (fields != null) {
            for (String field : fields) {
                selectedColumns.add(field);
            }
        }
        return this;
    }

    // --- Execution Methods (Auto-resolving Repository) ---

    private JpaSpecificationExecutor<T> getRepository() {
        return BeanUtil.getRepository(entityClass);
    }

    @SuppressWarnings("unchecked")
    public List<Object> get() {
        if (!selectedColumns.isEmpty()) {
            return executeProjection();
        }

        JpaSpecificationExecutor<T> repo = getRepository();
        if (sortOrders.isEmpty()) {
            return new ArrayList<>(repo.findAll(specification));
        }
        return new ArrayList<>(repo.findAll(specification, Sort.by(sortOrders)));
    }

    private List<Object> executeProjection() {
        jakarta.persistence.EntityManager em = BeanUtil.getEntityManager();
        jakarta.persistence.criteria.CriteriaBuilder cb = em.getCriteriaBuilder();
        jakarta.persistence.criteria.CriteriaQuery<jakarta.persistence.Tuple> query = cb.createTupleQuery();
        jakarta.persistence.criteria.Root<T> root = query.from(entityClass);

        List<jakarta.persistence.criteria.Selection<?>> selections = new ArrayList<>();
        for (String col : selectedColumns) {
            selections.add(root.get(col).alias(col));
        }
        query.multiselect(selections);

        // Apply Specification Predicate
        if (specification != null) {
            jakarta.persistence.criteria.Predicate predicate = specification.toPredicate(root, query, cb);
            if (predicate != null) {
                query.where(predicate);
            }
        }

        // Apply Ordering
        if (!sortOrders.isEmpty()) {
            List<jakarta.persistence.criteria.Order> orders = new ArrayList<>();
            for (Sort.Order sortOrder : sortOrders) {
                if (sortOrder.isAscending()) {
                    orders.add(cb.asc(root.get(sortOrder.getProperty())));
                } else {
                    orders.add(cb.desc(root.get(sortOrder.getProperty())));
                }
            }
            query.orderBy(orders);
        }

        List<jakarta.persistence.Tuple> tuples = em.createQuery(query).getResultList();

        // Convert Tuples to Maps
        List<Object> results = new ArrayList<>();
        for (jakarta.persistence.Tuple tuple : tuples) {
            java.util.Map<String, Object> map = new java.util.HashMap<>();
            for (jakarta.persistence.TupleElement<?> element : tuple.getElements()) {
                map.put(element.getAlias(), tuple.get(element));
            }
            results.add(map);
        }
        return results;
    }

    public Optional<T> first() {
        return getRepository().findOne(specification);
    }

    public Page<T> paginate(int page, int size) {
        PageRequest pageRequest;
        if (sortOrders.isEmpty()) {
            pageRequest = PageRequest.of(page, size);
        } else {
            pageRequest = PageRequest.of(page, size, Sort.by(sortOrders));
        }
        return getRepository().findAll(specification, pageRequest);
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
