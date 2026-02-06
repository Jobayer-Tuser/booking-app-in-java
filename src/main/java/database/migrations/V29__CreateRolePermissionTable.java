package database.migrations;

import database.migrations.library.Schema;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import java.sql.SQLException;


public class V29__CreateRolePermissionTable extends BaseJavaMigration {

    @Override
    public void migrate(Context context) throws SQLException {

        Schema.create("role_permission", table -> {
            table.foreignId("role_id").constrained();
            table.foreignId("permission_id").constrained();
        }, context);
        IO.println("✓ Role Permission created table successfully");
    }
}