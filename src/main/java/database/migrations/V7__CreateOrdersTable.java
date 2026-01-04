package database.migration;

import org.flywaydb.core.api.migration.Context;

import java.sql.SQLException;


public class V7__CreateOrdersTable extends BaseMigration {

    @Override
    public void migrate(Context context) throws SQLException {
        createTable(context, "orders", """
            `id` bigint unsigned NOT NULL AUTO_INCREMENT,
            `customer_id` bigint unsigned DEFAULT NULL,
            `status` enum('Canceled','Confirmed','Delivered','Paid','Pending') COLLATE utf8mb4_unicode_ci DEFAULT 'Pending',
            `total_price` decimal(10,2) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
            `created_at` timestamp DEFAULT NULL,
            PRIMARY KEY (`id`),
            KEY `orders_customer_id_foreign` (`customer_id`),
            CONSTRAINT `orders_customer_id_foreign` FOREIGN KEY (`customer_id`) REFERENCES `users` (`id`)
        """);

        IO.println("✓ Orders table created successfully");
    }
}