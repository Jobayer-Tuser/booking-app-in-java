package database.migrations;

import database.migrations.library.BaseMigration;
import database.migrations.library.Schema;

import java.sql.SQLException;

public class V10__CreateCountriesTable extends BaseMigration {

    @Override
    protected void run(Schema schema) throws SQLException {
        schema.create("country", table -> {
            table.id();
            table.string("name");
            table.decimal("latitude", 10, 7);
            table.decimal("longitude", 10, 7);
            table.timestamps();
        });
        log("Countries table created successfully");
    }
}