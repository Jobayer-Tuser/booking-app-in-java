package database.migrations;

import database.migrations.library.BaseMigration;
import database.migrations.library.Schema;

import java.sql.SQLException;

public class V31__AddCreatedByUpdatedByToUsersTable extends BaseMigration {

    @Override
    protected void run(Schema schema) throws SQLException {
        schema.table("users", table -> {
            table.integer("created_by").nullable();
            table.integer("updated_by").nullable();
            table.datetime("deleted_at");
        });
        log("Created by and updated by columns added to users table successfully");
    }
}