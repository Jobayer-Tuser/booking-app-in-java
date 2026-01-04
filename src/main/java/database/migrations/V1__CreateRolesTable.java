package database.migrations;

import database.migrations.library.Schema;
import org.flywaydb.core.api.migration.Context;

import java.sql.SQLException;


public class V1__CreateRolesTable extends BaseMigration {

    @Override
    public void migrate(Context context) throws SQLException {
        Schema.create("roles", table -> {
            table.id();
            table.string("name");
            table.timestamps();
        }, context);

        IO.println("✓ Roles table created successfully");
    }
}