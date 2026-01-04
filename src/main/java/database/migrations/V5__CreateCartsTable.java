package database.migrations;

import database.migrations.library.Schema;
import org.flywaydb.core.api.migration.Context;

import java.sql.SQLException;


public class V5__CreateCartsTable extends BaseMigration {

    @Override
    public void migrate(Context context) throws SQLException {
        Schema.create("carts", table -> {
            table.id();
            table.timeStamp("created_id");
        }, context);

        IO.println("✓ Carts table created successfully");
    }
}