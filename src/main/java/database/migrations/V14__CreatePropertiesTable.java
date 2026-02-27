package database.migrations;

import database.migrations.library.BaseMigration;
import database.migrations.library.Schema;

import java.sql.SQLException;

public class V14__CreatePropertiesTable extends BaseMigration {

    @Override
    protected void run(Schema schema) throws SQLException {
        schema.create("properties", table -> {
            table.id();
            table.foreignId("owner_id").constrained("users");
            table.foreignId("city_id").constrained("cities");
            table.string("name");
            table.string("address_street");
            table.string("address_postcode");
            table.decimal("latitude", 10, 7);
            table.decimal("longitude", 10, 7);
            table.timestamps();
        });
        log("Properties table created successfully");
    }
}