package database.migrations;

import database.migrations.library.Schema;
import org.flywaydb.core.api.migration.Context;

import java.sql.SQLException;


public class V16__CreateTableForExample extends BaseMigration {

    @Override
    public void migrate(Context context) throws SQLException {
        Schema.create("examples", table -> {
            table.id();
            table.foreignId("user_id").notNull().constrained("users").onUpdateCascade().onDeleteRestrict();
            table.foreignId("role_id").constrained("roles").onUpdateRestrict().onDeleteCascade();
            table.integer("number_plate").unsigned();
            table.string("email").unique();
            table.string("number").unique();
            table.enumeration("role", "Admin", "User", "Editor").defaultValue("User");
            table.string("invoice_address", 200).notNull();
            table.string("invoice_postcode", 200);
            table.decimal("latitude", 10, 2);
            table.decimal("longitude", 10, 2);
            table.datetime("email_verified");
            table.timestamps();
        }, context);
    }
}