package database.migrations;

import database.migrations.library.BaseMigration;
import database.migrations.library.Schema;

import java.sql.SQLException;

public class V21__CreateRoomTable extends BaseMigration {

    @Override
    protected void run(Schema schema) throws SQLException {
        schema.create("rooms", table -> {
            table.id();
            table.foreignId("apartment_id").constrained();
            table.foreignId("room_type_id").nullable().constrained();
            table.string("name");
            table.timestamps();
        });
        log("Rooms table created successfully");
    }
}