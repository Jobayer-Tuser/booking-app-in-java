package database.migrations;

import database.migrations.library.BaseMigration;
import database.migrations.library.Schema;

import java.sql.SQLException;

public class V4__CreateProductsTable extends BaseMigration {

    @Override
    protected void run(Schema schema) throws SQLException {
        schema.create("products", table -> {
            table.id();
            table.foreignId("category_id").constrained("categories").onUpdateCascade().onDeleteRestrict();
            table.string("name");
            table.decimal("price", 10, 2);
            table.timestamps();
        });
        log("Products table created successfully");
    }
}