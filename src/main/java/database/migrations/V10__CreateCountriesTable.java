package database.migration;

import org.flywaydb.core.api.migration.Context;

import java.sql.SQLException;


public class V10__CreateCountriesTable extends BaseMigration {

    @Override
    public void migrate(Context context) throws SQLException {
        createTable(context, "countries", """
             `id` bigint unsigned NOT NULL AUTO_INCREMENT,
             `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
             `latitude` decimal(10,7) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
             `longitude` decimal(10,7) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
             `created_at` timestamp NULL DEFAULT NULL,
             `updated_at` timestamp NULL DEFAULT NULL,
             PRIMARY KEY (`id`)
        """);

        IO.println("✓ Countries table created successfully");
    }
}