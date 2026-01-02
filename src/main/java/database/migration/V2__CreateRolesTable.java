package database.migration;

import org.flywaydb.core.api.migration.Context;

import java.sql.SQLException;


public class V2__CreateRolesTable extends BaseMigration {

    @Override
    public void migrate(Context context) throws SQLException {
        createTable(context, "roles", """
             `id` bigint unsigned NOT NULL AUTO_INCREMENT,
             `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
             `created_at` timestamp NULL DEFAULT NULL,
             `updated_at` timestamp NULL DEFAULT NULL,
             PRIMARY KEY (`id`)
        """);

        // Add role_id to users
        addColumn(context, "users", "role_id bigint unsigned DEFAULT 1 COMMENT \"user,admin,editor\"");
        addForeignKey(context, "users", "fk_users_role_id", "role_id", "roles", "id");

        IO.println("✓ Roles table created successfully");
    }
}