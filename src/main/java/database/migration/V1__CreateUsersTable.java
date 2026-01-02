package database.migration;

import org.flywaydb.core.api.migration.Context;

import java.sql.SQLException;

public class V1__CreateUsersTable extends BaseMigration {

    @Override
    public void migrate(Context context) throws SQLException {
        String query = """
            CREATE TABLE `users` (
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
                 KEY `users_role_id_foreign` (`role_id`),
                 CONSTRAINT `users_role_id_foreign` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
            ) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
            """;
    }
}