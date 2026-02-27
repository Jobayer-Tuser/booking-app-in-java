package database.migrations;

import database.migrations.library.BaseMigration;
import database.migrations.library.Schema;

import java.sql.SQLException;

public class V13__CreateGeoObjectsTable extends BaseMigration {

    @Override
    protected void run(Schema schema) throws SQLException {
        schema.create("geo_objects", table -> {
            table.id();
            table.foreignId("city_id").constrained("cities");
            table.string("name");
            table.decimal("latitude", 10, 7);
            table.decimal("longitude", 10, 7);
            table.timestamps();
        });
        log("Geo Objects table created successfully");
    }
}