package database.migrations;

import database.migrations.library.Schema;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import java.sql.SQLException;


public class V26__CreateApartmentFacilityTable extends BaseJavaMigration {

    @Override
    public void migrate(Context context) throws SQLException {

        Schema.create("apartment_facility", table -> {
            table.id();
            table.foreignId("facility_id");
            table.foreignId("apartment_id");
        }, context);
        IO.println("✓ apartments facility table successfully");
    }
}