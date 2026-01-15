package database.migrations;

import database.migrations.library.Schema;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import java.sql.SQLException;


public class V20__CreateRoomTypeTable extends BaseJavaMigration {

    @Override
    public void migrate(Context context) throws SQLException {

        Schema.create("room_types", table -> {
            table.id();
            table.string("name");
            table.timestamps();
        }, context);
        IO.println("✓ Created room type table successfully");
    }
}