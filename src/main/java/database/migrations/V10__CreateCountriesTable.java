package database.migrations;

import database.migrations.library.Schema;
import org.flywaydb.core.api.migration.Context;

import java.sql.SQLException;


public class V10__CreateCountriesTable extends BaseMigration {

    @Override
    public void migrate(Context context) throws SQLException {

        Schema.create("countries", table -> {
            table.id();
            table.string("name").notNull();
            table.decimal("latitude", 10, 7);
            table.decimal("longitude", 10, 7);
            table.timestamps();
        }, context);

        IO.println("✓ Countries table created successfully");
    }
}