package database.migration;

import org.flywaydb.core.api.migration.Context;

import java.sql.SQLException;


public class V8__CreateOrderItemsTable extends BaseMigration {

    @Override
    public void migrate(Context context) throws SQLException {
        createTable(context, "order_items", """
            `id` bigint unsigned NOT NULL AUTO_INCREMENT,
            `product_id` bigint unsigned DEFAULT NULL,
            `order_id` bigint unsigned DEFAULT NULL,
            `quantity` int COLLATE utf8mb4_unicode_ci DEFAULT NULL,
            `unit_price` decimal(10,2) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
            `total_price` decimal(10,2) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
            PRIMARY KEY (`id`),
            KEY `order_items_product_id_foreign` (`product_id`),
            KEY `order_items_order_id_foreign` (`order_id`),
            CONSTRAINT `order_items_order_id_foreign` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
            CONSTRAINT `order_items_product_id_foreign` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
        """);

        IO.println("✓ Order Items table created successfully");
    }
}