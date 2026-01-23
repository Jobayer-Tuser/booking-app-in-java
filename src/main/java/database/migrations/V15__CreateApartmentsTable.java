package database.migrations;

import database.migrations.library.Schema;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import java.sql.SQLException;


public class V15__CreateApartmentsTable extends BaseJavaMigration {

    @Override
    public void migrate(Context context) throws SQLException {

        Schema.create("apartments", table -> {
            table.id();
            table.foreignId("property_id").constrained("properties");
            table.string("name");
            table.integer("capacity_adults").unsigned();
            table.integer("capacity_children").unsigned();
            table.timestamps();
        }, context);

        IO.println("✓ Apartments table created successfully");
    }
}