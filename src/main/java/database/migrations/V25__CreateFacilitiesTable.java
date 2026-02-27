package database.migrations;

import database.migrations.library.BaseMigration;
import database.migrations.library.Schema;

import java.sql.SQLException;

public class V25__CreateFacilitiesTable extends BaseMigration {

    @Override
    protected void run(Schema schema) throws SQLException {
        schema.create("facilities", table -> {
            table.id();
            table.foreignId("facility_category_id").nullable().constrained("facility_categories");
            table.string("name");
        });
        log("Facilities table created successfully");
    }
}