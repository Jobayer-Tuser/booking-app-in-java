package database.migration;

import org.flywaydb.core.api.migration.Context;

import java.sql.SQLException;


public class V6__CreateCartItemsTable extends BaseMigration {

    @Override
    public void migrate(Context context) throws SQLException {
        createTable(context, "cart_items", """
            `id` bigint unsigned NOT NULL AUTO_INCREMENT,
            `cart_id` binary(16) NOT NULL,
            `product_id` bigint unsigned NOT NULL,
            `quantity` int NOT NULL,
            PRIMARY KEY (`id`),
            KEY `cart_items_cart_id_foreign` (`cart_id`),
            KEY `cart_items_product_id_foreign` (`product_id`),
            CONSTRAINT `cart_items_product_id_foreign` FOREIGN KEY (`cart_id`) REFERENCES `carts` (`id`) ON UPDATE CASCADE ON DELETE CASCADE,
            CONSTRAINT `cart_items_cart_id_foreign` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`) ON UPDATE CASCADE ON DELETE CASCADE
        """);

        IO.println("✓ CartItems table created successfully");
    }
}