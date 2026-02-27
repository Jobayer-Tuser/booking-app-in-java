package database.migrations;

import database.migrations.library.BaseMigration;
import database.migrations.library.Schema;

import java.sql.SQLException;

public class V15__CreateApartmentsTable extends BaseMigration {

    @Override
    protected void run(Schema schema) throws SQLException {
        schema.create("apartments", table -> {
            table.id();
            table.foreignId("property_id").constrained("properties");
            table.string("name");
            table.integer("capacity_adults").unsigned();
            table.integer("capacity_children").unsigned();
            table.timestamps();
        });
        log("Apartments table created successfully");
    }
}