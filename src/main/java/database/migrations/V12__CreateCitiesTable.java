package database.migrations;

import database.migrations.library.BaseMigration;
import database.migrations.library.Schema;

import java.sql.SQLException;

public class V12__CreateCitiesTable extends BaseMigration {

    @Override
    protected void run(Schema schema) throws SQLException {
        schema.create("cities", table -> {
            table.id();
            table.foreignId("country_id").constrained("country").onUpdateCascade().onDeleteRestrict();
            table.string("name");
            table.decimal("latitude", 10, 7);
            table.decimal("longitude", 10, 7);
            table.timestamps();
        });
        log("Cities table created successfully");
    }
}