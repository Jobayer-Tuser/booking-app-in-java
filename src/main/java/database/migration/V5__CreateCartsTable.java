package database.migration;

import org.flywaydb.core.api.migration.Context;

import java.sql.SQLException;


public class V5__CreateCartsTable extends BaseMigration {

    @Override
    public void migrate(Context context) throws SQLException {
        createTable(context, "carts", """
            `id` binary(16) NOT NULL DEFAULT (uuid_to_bin(uuid())),
            `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
             PRIMARY KEY (`id`)
        """);

        IO.println("✓ Carts table created successfully");
    }
}