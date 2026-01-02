package database.migration;

import org.flywaydb.core.api.migration.Context;

import java.sql.SQLException;


public class V12__CreateCitiesTable extends BaseMigration {

    @Override
    public void migrate(Context context) throws SQLException {
        createTable(context, "cities", """
            `id` bigint unsigned NOT NULL AUTO_INCREMENT,
            `country_id` bigint unsigned NOT NULL,
            `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
            `latitude` decimal(10,7) DEFAULT NULL,
            `longitude` decimal(10,7) DEFAULT NULL,
            `created_at` timestamp NULL DEFAULT NULL,
            `updated_at` timestamp NULL DEFAULT NULL,
            PRIMARY KEY (`id`),
            KEY `cities_country_id_foreign` (`country_id`),
            CONSTRAINT `cities_country_id_foreign` FOREIGN KEY (`country_id`) REFERENCES `countries` (`id`)
        """);

        IO.println("✓ Cities table created successfully");
    }
}