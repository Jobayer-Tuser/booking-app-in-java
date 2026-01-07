package database.migrations;

import database.migrations.library.Schema;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import java.sql.SQLException;


public class V14__CreatePropertiesTable extends BaseJavaMigration {

    @Override
    public void migrate(Context context) throws SQLException {

        Schema.create("properties", table -> {
            table.id();
            table.foreignId("owner_id").constrained("users");
            table.foreignId("city_id").constrained("cities");
            table.string("name");
            table.string("address_street");
            table.string("address_postcode");
            table.decimal("latitude", 10, 7);
            table.decimal("longitude", 10, 7);
            table.timestamps();
        }, context);

        IO.println("✓ Properties table created successfully");
    }
}