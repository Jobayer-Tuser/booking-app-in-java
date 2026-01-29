package database.migrations;

import database.migrations.library.Schema;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import java.sql.SQLException;


public class V27__CreatePropertyFacilityTable extends BaseJavaMigration {

    @Override
    public void migrate(Context context) throws SQLException {

        Schema.create("property_facility", table -> {
            table.foreignId("property_id");
            table.foreignId("facility_id");
        }, context);
        IO.println("✓ apartments facility table successfully");
    }
}