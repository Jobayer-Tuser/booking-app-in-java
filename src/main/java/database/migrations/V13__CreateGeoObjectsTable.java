package database.migrations;

import database.migrations.library.Schema;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import java.sql.SQLException;


public class V13__CreateGeoObjectsTable extends BaseJavaMigration {

    @Override
    public void migrate(Context context) throws SQLException {

        Schema.create("geo_objects", table -> {
            table.id();
            table.foreignId("city_id").constrained("City");
            table.string("name");
            table.decimal("latitude", 10, 7);
            table.decimal("longitude", 10, 7);
            table.timestamps();
        }, context);

        IO.println("✓ Geo Objects table created successfully");
    }
}