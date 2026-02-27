package database.migrations;

import database.migrations.library.BaseMigration;
import database.migrations.library.Schema;

import java.sql.SQLException;

public class V24__CreateFacilityCategoriesTable extends BaseMigration {

    @Override
    protected void run(Schema schema) throws SQLException {
        schema.create("facility_categories", table -> {
            table.id();
            table.string("name");
            table.timestamps();
        });
        log("Facility categories table created successfully");
    }
}