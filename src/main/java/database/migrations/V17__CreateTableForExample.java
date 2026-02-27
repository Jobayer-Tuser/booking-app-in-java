package database.migrations;

import database.migrations.library.BaseMigration;
import database.migrations.library.Schema;

import java.sql.SQLException;

public class V17__CreateTableForExample extends BaseMigration {

    @Override
    protected void run(Schema schema) throws SQLException {
        schema.create("examples", table -> {
            table.id();
            table.foreignId("user_id").constrained("users").onUpdateCascade().onDeleteRestrict();
            table.integer("number_plate").unsigned();
            table.string("email").unique();
            table.string("number").unique();
            table.enumeration("roles", "Admin", "users", "Editor").defaultValue("users");
            table.string("invoice_address", 200);
            table.string("invoice_postcode", 200);
            table.decimal("latitude", 10, 2);
            table.decimal("longitude", 10, 2);
            table.datetime("email_verified");
            table.timestamps();
        });
    }
}