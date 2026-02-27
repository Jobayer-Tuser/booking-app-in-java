package database.migrations;

import database.migrations.library.BaseMigration;
import database.migrations.library.Schema;

import java.sql.SQLException;

public class V2__CreateUsersTable extends BaseMigration {

    @Override
    protected void run(Schema schema) throws SQLException {
        schema.create("users", table -> {
            table.id();
            table.foreignId("role_id").constrained().onUpdateCascade().onDeleteRestrict();
            table.string("name");
            table.string("email").unique();
            table.string("display_name");
            table.string("password");
            table.datetime("email_verified_at");
            table.timestamps();
        });
        log("Users table created successfully");
    }
}