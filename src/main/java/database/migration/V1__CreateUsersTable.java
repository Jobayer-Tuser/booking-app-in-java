package database.migration;

import org.flywaydb.core.api.migration.Context;

import java.sql.SQLException;

public class V1__CreateUsersTable extends BaseMigration {

    @Override
    public void migrate(Context context) throws SQLException {

        createTable(context, "users", """
             `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
             `role_id` bigint UNSIGNED COLLATE utf8mb4_unicode_ci DEFAULT 1 COMMENT "user,admin,editor",
             `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
             `email` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
             `display_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
             `password` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
             `email_verified_at` datetime(6) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
             `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
             `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
             PRIMARY KEY (`id`),
             UNIQUE KEY `app_users_email_unique` (`email`),
        """);

        IO.println("✓ Users table created successfully");
    }
}