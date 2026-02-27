package database.migrations;

import database.migrations.library.BaseMigration;
import database.migrations.library.Schema;

import java.sql.SQLException;

public class V22__CreateBedTypeTable extends BaseMigration {

    @Override
    protected void run(Schema schema) throws SQLException {
        schema.create("bed_types", table -> {
            table.id();
            table.string("name");
            table.timestamps();
        });
        log("Bed types table created successfully");
    }
}