package database.migrations;

import database.migrations.library.BaseMigration;
import database.migrations.library.Schema;

import java.sql.SQLException;

public class V9__CreateAddressesTable extends BaseMigration {

    @Override
    protected void run(Schema schema) throws SQLException {
        schema.create("addresses", table -> {
            table.id();
            table.foreignId("user_id").constrained("users").onUpdateCascade().onDeleteRestrict();
            table.string("zip");
            table.string("city");
            table.string("state");
        });
        log("Addresses table created successfully");
    }
}