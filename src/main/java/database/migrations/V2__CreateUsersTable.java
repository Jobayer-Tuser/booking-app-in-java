package database.migrations;

import database.migrations.library.Schema;
import org.flywaydb.core.api.migration.Context;

import java.sql.SQLException;

public class V1__CreateUsersTable extends BaseMigration {

    @Override
    public void migrate(Context context) throws SQLException {

        Schema.create("users", table -> {
            table.id();
            table.string("name");
            table.string("email").unique();
            table.string("display_name");
            table.string("password");
            table.datetime("email_verified_at");
            table.timestamps();
        }, context);

        IO.println("✓ Users table created successfully");
    }
}