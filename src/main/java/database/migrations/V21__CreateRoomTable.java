package database.migrations;

import database.migrations.library.Schema;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import java.sql.SQLException;


public class V21__CreateRoomTable extends BaseJavaMigration {

    @Override
    public void migrate(Context context) throws SQLException {

        Schema.create("rooms", table -> {
            table.id();
            table.foreignId("apartment_id").constrained();
            table.foreignId("room_type_id").nullable().constrained();
            table.string("name");
            table.timestamps();
        }, context);
        IO.println("✓ Created room type table successfully");
    }
}