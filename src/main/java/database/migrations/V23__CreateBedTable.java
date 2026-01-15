package database.migrations;

import database.migrations.library.Schema;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import java.sql.SQLException;


public class V23__CreateBedTable extends BaseJavaMigration {

    @Override
    public void migrate(Context context) throws SQLException {

        Schema.create("beds", table -> {
            table.id();
            table.foreignId("room_id").constrained();
            table.foreignId("bed_type_id").constrained();
            table.string("name").nullable();
            table.timestamps();
        }, context);
        IO.println("✓ Created room type table successfully");
    }
}