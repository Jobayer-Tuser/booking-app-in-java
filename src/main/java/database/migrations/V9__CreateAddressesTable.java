package database.migrations;

import database.migrations.library.Schema;
import org.flywaydb.core.api.migration.Context;

import java.sql.SQLException;


public class V9__CreateAddressesTable extends BaseMigration {

    @Override
    public void migrate(Context context) throws SQLException {
        Schema.create("addresses", table -> {
            table.id();
            table.foreignId("user_id").constrained("users").onUpdateCascade().onDeleteRestrict();
            table.string("zip");
            table.string("city");
            table.string("state");
        }, context);

        IO.println("✓ Addresses table created successfully");
    }
}