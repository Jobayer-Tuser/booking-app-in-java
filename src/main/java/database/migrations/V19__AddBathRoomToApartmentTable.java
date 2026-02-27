package database.migrations;

import database.migrations.library.BaseMigration;
import database.migrations.library.Schema;

import java.sql.SQLException;

public class V19__AddBathRoomToApartmentTable extends BaseMigration {

    @Override
    protected void run(Schema schema) throws SQLException {
        schema.table("apartments", table -> {
            table.integer("bathroom").unsigned().defaultValue(0);
        });
        log("Bathroom column added to apartments table successfully");
    }
}