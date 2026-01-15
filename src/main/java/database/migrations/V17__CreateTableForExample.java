package database.migrations;

import database.migrations.library.Schema;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;


public class V17__CreateTableForExample extends BaseJavaMigration {

    @Override
    public void migrate(Context context) throws SQLException, IOException {
        Schema.create("examples", table -> {
            table.id();
            table.foreignId("user_id").constrained("users").onUpdateCascade().onDeleteRestrict();
//            table.foreignId("role_id").constrained("roles").onUpdateRestrict().onDeleteCascade();
            table.integer("number_plate").unsigned();
            table.string("email").unique();
            table.string("number").unique();
            table.enumeration("Role", "Admin", "User", "Editor").defaultValue("User");
            table.string("invoice_address", 200);
            table.string("invoice_postcode", 200);
            table.decimal("latitude", 10, 2);
            table.decimal("longitude", 10, 2);
            table.datetime("email_verified");
            table.timestamps();
        }, context);
    }
}