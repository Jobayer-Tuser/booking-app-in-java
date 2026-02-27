package database.migrations;

import database.migrations.library.BaseMigration;
import database.migrations.library.Schema;

import java.sql.SQLException;

public class V27__CreatePropertyFacilityTable extends BaseMigration {

    @Override
    protected void run(Schema schema) throws SQLException {
        schema.create("property_facility", table -> {
            table.foreignId("property_id");
            table.foreignId("facility_id");
        });
        log("Property facility table created successfully");
    }
}