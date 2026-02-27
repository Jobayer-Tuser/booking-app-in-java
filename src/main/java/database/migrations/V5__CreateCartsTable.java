package database.migrations;

import database.migrations.library.BaseMigration;
import database.migrations.library.Schema;

import java.sql.SQLException;

public class V5__CreateCartsTable extends BaseMigration {

    @Override
    protected void run(Schema schema) throws SQLException {
        schema.create("carts", table -> {
            table.id();
            table.timeStamp("created_id");
        });
        log("Carts table created successfully");
    }
}