package database.migrations;

import database.migrations.library.BaseMigration;
import database.migrations.library.Schema;

import java.sql.SQLException;

public class V16__CreateApartmentTypesTable extends BaseMigration {

    @Override
    protected void run(Schema schema) throws SQLException {
        schema.create("apartment_types", table -> {
            table.id();
            table.string("name");
            table.timestamps();
        });
        log("Apartment types table created successfully");
    }
}