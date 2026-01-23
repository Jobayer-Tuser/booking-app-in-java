package database.migrations;

import database.migrations.library.Schema;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import java.sql.SQLException;


public class V12__CreateCitiesTable extends BaseJavaMigration {

    @Override
    public void migrate(Context context) throws SQLException {

        Schema.create("cities", table -> {
            table.id();
            table.foreignId("country_id").constrained("country").onUpdateCascade().onDeleteRestrict();
            table.string("name");
            table.decimal("latitude", 10, 7);
            table.decimal("longitude", 10, 7);
            table.timestamps();
        }, context);

        IO.println("✓ Cities table created successfully");
    }
}