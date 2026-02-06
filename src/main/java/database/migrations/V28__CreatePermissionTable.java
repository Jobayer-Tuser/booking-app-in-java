package database.migrations;

import database.migrations.library.Schema;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import java.sql.SQLException;


public class V28__CreatePermissionTable extends BaseJavaMigration {

    @Override
    public void migrate(Context context) throws SQLException {

        Schema.create("permissions", table -> {
            table.id();
            table.string("name");
        }, context);
        IO.println("✓ Permission created table successfully");
    }
}