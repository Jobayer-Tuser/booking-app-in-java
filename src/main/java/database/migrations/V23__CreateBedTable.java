package database.migrations;

import database.migrations.library.BaseMigration;
import database.migrations.library.Schema;

import java.sql.SQLException;

public class V23__CreateBedTable extends BaseMigration {

    @Override
    protected void run(Schema schema) throws SQLException {
        schema.create("beds", table -> {
            table.id();
            table.foreignId("room_id").constrained();
            table.foreignId("bed_type_id").constrained();
            table.string("name").nullable();
            table.timestamps();
        });
        log("Beds table created successfully");
    }
}