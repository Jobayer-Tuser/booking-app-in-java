package database.migrations;

import database.migrations.library.Schema;
import org.flywaydb.core.api.migration.Context;

import java.sql.SQLException;

public class V2__CreateUsersTable extends BaseMigration {

    @Override
    public void migrate(Context context) throws SQLException {
        Schema.create("users", table -> {
            table.id();
            table.foreignId("role_id").constrained("roles").onUpdateCascade().onDeleteRestrict();
            table.string("name").notNull();
            table.string("email").unique();
            table.string("display_name");
            table.string("password").notNull();
            table.datetime("email_verified_at");
            table.timestamps();
        }, context);

        IO.println("✓ Users table created successfully");
    }
}