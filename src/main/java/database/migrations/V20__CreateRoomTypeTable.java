package database.migrations;

import database.migrations.library.BaseMigration;
import database.migrations.library.Schema;

import java.sql.SQLException;

public class V20__CreateRoomTypeTable extends BaseMigration {

    @Override
    protected void run(Schema schema) throws SQLException {
        schema.create("room_types", table -> {
            table.id();
            table.string("name");
            table.timestamps();
        });
        log("Room type table created successfully");
    }
}