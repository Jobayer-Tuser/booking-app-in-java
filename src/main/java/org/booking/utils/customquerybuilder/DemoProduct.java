package org.booking.utils.customquerybuilder;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "demo_products")
@Data
public class DemoProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String category;
    private Integer price;

    // --- Active Record Static Helpers ---

    public static EloquentQueryBuilder<DemoProduct> query() {
        return new EloquentQueryBuilder<>(DemoProduct.class);
    }

    public static EloquentQueryBuilder<DemoProduct> where(String field, Object value) {
        return query().where(field, value);
    }

    public static EloquentQueryBuilder<DemoProduct> where(String field, String operator, Object value) {
        return query().where(field, operator, value);
    }

    public static EloquentQueryBuilder<DemoProduct> select(String... fields) {
        return query().select(fields);
    }

    public static List<DemoProduct> all() {
        return query().get();
    }
}
