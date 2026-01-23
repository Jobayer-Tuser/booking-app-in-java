package database.migrations;

import database.migrations.library.Schema;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import java.sql.SQLException;


public class V18__AddApartmentTypeToApartmentTable extends BaseJavaMigration {

    @Override
    public void migrate(Context context) throws SQLException {

        Schema.table("apartments", table -> {
            table.foreignId("apartment_type_id").constrained("apartment_types").after("id");
            table.integer("size").unsigned().defaultValue(0);
        }, context);
        IO.println("✓ apartments Types table created successfully");
    }
}