package database.migration;

import org.flywaydb.core.api.migration.Context;

import java.sql.SQLException;


public class V9__CreateAddressesTable extends BaseMigration {

    @Override
    public void migrate(Context context) throws SQLException {
        createTable(context, "addresses", """
             `id` bigint unsigned NOT NULL AUTO_INCREMENT,
             `user_id` bigint unsigned NOT NULL,
             `street` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
             `zip` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
             `city` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
             `state` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
             PRIMARY KEY (`id`),
             KEY `addresses_user_id_foreign` (`user_id`),
             CONSTRAINT `addresses_user_id_foreign` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON UPDATE CASCADE ON DELETE RESTRICT
        """);

        IO.println("✓ Addresses table created successfully");
    }
}