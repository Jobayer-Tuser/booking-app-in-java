package database.migrations;

import database.migrations.library.Schema;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import java.sql.SQLException;


public class V19__AddBathRoomToApartmentTable extends BaseJavaMigration {

    @Override
    public void migrate(Context context) throws SQLException {

        Schema.table("apartments", table -> {
            table.integer("bathroom").unsigned().defaultValue(0);
        }, context);
        IO.println("✓ Added bathroom to apartments table successfully");
    }
}