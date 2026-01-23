package database.migrations;

import database.migrations.library.Schema;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import java.sql.SQLException;


public class V25__CreateFacilitiesTable extends BaseJavaMigration {

    @Override
    public void migrate(Context context) throws SQLException {

        Schema.create("facilities", table -> {
            table.id();
            table.foreignId("facility_category_id").nullable().constrained("facility_categories");
            table.string("name");
        }, context);
        IO.println("✓ Facilities table successfully");
    }
}