package database.migration;

import org.flywaydb.core.api.migration.Context;

import java.sql.SQLException;


public class V11__CreateProfilesTable extends BaseMigration {

    @Override
    public void migrate(Context context) throws SQLException {
        createTable(context, "profiles", """
             `id` bigint unsigned NOT NULL AUTO_INCREMENT,
             `user_id` bigint unsigned NOT NULL,
             `gender` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
             `birth_date` date DEFAULT NULL,
             `description` text COLLATE utf8mb4_unicode_ci,
             `invoice_address` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
             `invoice_postcode` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
             `invoice_city` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
             `invoice_country_id` bigint unsigned DEFAULT NULL,
             `nationality_country_id` bigint unsigned DEFAULT NULL,
             `created_at` timestamp NULL DEFAULT NULL,
             `updated_at` timestamp NULL DEFAULT NULL,
             PRIMARY KEY (`id`),
             KEY `user_profiles_user_id_foreign` (`user_id`),
             KEY `user_profiles_invoice_country_id_foreign` (`invoice_country_id`),
             KEY `user_profiles_nationality_country_id_foreign` (`nationality_country_id`),
             CONSTRAINT `user_profiles_invoice_country_id_foreign` FOREIGN KEY (`invoice_country_id`) REFERENCES `countries` (`id`),
             CONSTRAINT `user_profiles_nationality_country_id_foreign` FOREIGN KEY (`nationality_country_id`) REFERENCES `countries` (`id`),
             CONSTRAINT `user_profiles_user_id_foreign` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
        """);

        IO.println("✓ Profiles table created successfully");
    }
}