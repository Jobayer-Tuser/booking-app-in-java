package database.migrations;

import database.migrations.library.Schema;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import java.sql.SQLException;


public class V24__CreateFacilityCategoriesTable extends BaseJavaMigration {

    @Override
    public void migrate(Context context) throws SQLException {

        Schema.create("facility_categories", table -> {
            table.id();
            table.string("name");
            table.timestamps();
        }, context);
        IO.println("✓ facility Categories table successfully");
    }
}