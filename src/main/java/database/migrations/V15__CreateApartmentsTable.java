package database.migration;

import org.flywaydb.core.api.migration.Context;

import java.sql.SQLException;


public class V15__CreateApartmentsTable extends BaseMigration {

    @Override
    public void migrate(Context context) throws SQLException {
        createTable(context, "apartments", """
            `id` bigint unsigned NOT NULL AUTO_INCREMENT,
            `property_id` bigint unsigned NOT NULL,
            `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
            `capacity_adults` int unsigned NOT NULL,
            `capacity_children` int unsigned NOT NULL,
            `created_at` timestamp NULL DEFAULT NULL,
            `updated_at` timestamp NULL DEFAULT NULL,
            PRIMARY KEY (`id`),
            KEY `apartments_property_id_foreign` (`property_id`),
            CONSTRAINT `apartments_property_id_foreign` FOREIGN KEY (`property_id`) REFERENCES `properties` (`id`)
        """);

        IO.println("✓ Apartments table created successfully");
    }
}