package database.migrations;

import database.migrations.library.BaseMigration;
import database.migrations.library.Schema;

import java.sql.SQLException;

public class V28__CreatePermissionTable extends BaseMigration {

    @Override
    protected void run(Schema schema) throws SQLException {
        schema.create("permissions", table -> {
            table.id();
            table.string("name");
        });
        log("Permissions table created successfully");
    }
}