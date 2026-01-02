package database.migration;

import org.flywaydb.core.api.migration.Context;

import java.sql.SQLException;


public class V4__CreateProductsTable extends BaseMigration {

    @Override
    public void migrate(Context context) throws SQLException {
        createTable(context, "products", """
            `id` bigint unsigned NOT NULL AUTO_INCREMENT,
            `category_id` tinyint DEFAULT NULL,
            `name` varchar(255) NOT NULL,
            `price` decimal(10,2) NOT NULL,
            PRIMARY KEY (`id`),
            KEY `products_category_id_foreign` (`category_id`),
            CONSTRAINT `products_category_id_foreign` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`) ON UPDATE CASCADE ON DELETE RESTRICT
        """);

        IO.println("✓ Products table created successfully");
    }
}