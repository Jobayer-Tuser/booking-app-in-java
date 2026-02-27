package database.migrations;

import database.migrations.library.BaseMigration;
import database.migrations.library.Schema;

import java.sql.SQLException;

public class V6__CreateCartItemsTable extends BaseMigration {

    @Override
    protected void run(Schema schema) throws SQLException {
        schema.create("cart_items", table -> {
            table.id();
            table.foreignId("cart_id").constrained("carts").onUpdateCascade().onDeleteRestrict();
            table.foreignId("product_id").constrained("products").onUpdateCascade().onDeleteRestrict();
            table.integer("quantity");
        });
        log("CartItems table created successfully");
    }
}