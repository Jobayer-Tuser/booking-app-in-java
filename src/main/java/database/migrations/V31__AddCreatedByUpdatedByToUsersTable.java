package database.migrations;

import database.migrations.library.Schema;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import java.sql.SQLException;


public class V31__AddCreatedByUpdatedByToUsersTable extends BaseJavaMigration {

    @Override
    public void migrate(Context context) throws SQLException {

        Schema.table("users", table -> {
            table.integer("created_by").nullable();
            table.integer("updated_by").nullable();
            table.datetime("deleted_at");
        }, context);
        IO.println("✓ Added Created By and Updated By to users table successfully");
    }
}