package database.migrations;

import database.migrations.library.BaseMigration;
import database.migrations.library.Schema;

import java.sql.SQLException;

public class V26__CreateApartmentFacilityTable extends BaseMigration {

    @Override
    protected void run(Schema schema) throws SQLException {
        schema.create("apartment_facility", table -> {
            table.id();
            table.foreignId("facility_id");
            table.foreignId("apartment_id");
        });
        log("Apartment facility table created successfully");
    }
}