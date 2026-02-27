package database.migrations;

import database.migrations.library.BaseMigration;
import database.migrations.library.Schema;

import java.sql.SQLException;

public class V1__CreateRolesTable extends BaseMigration {

    @Override
    protected void run(Schema schema) throws SQLException {
        schema.create("roles", table -> {
            table.id();
            table.string("name");
            table.timestamps();
        });
        log("Roles table created successfully");
    }
}