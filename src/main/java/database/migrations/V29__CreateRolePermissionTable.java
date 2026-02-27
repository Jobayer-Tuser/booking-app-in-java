package database.migrations;

import database.migrations.library.BaseMigration;
import database.migrations.library.Schema;

import java.sql.SQLException;

public class V29__CreateRolePermissionTable extends BaseMigration {

    @Override
    protected void run(Schema schema) throws SQLException {
        schema.create("role_permission", table -> {
            table.foreignId("role_id").constrained();
            table.foreignId("permission_id").constrained();
        });
        log("Role permission table created successfully");
    }
}