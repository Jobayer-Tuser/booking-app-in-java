package database.migrations;

import database.migrations.library.BaseMigration;
import database.migrations.library.Schema;

import java.sql.SQLException;

public class V8__CreateOrderItemsTable extends BaseMigration {

    @Override
    protected void run(Schema schema) throws SQLException {
        schema.create("order_items", table -> {
            table.id();
            table.foreignId("product_id").constrained("products").onUpdateCascade().onDeleteRestrict();
            table.foreignId("order_id").constrained("orders").onUpdateCascade().onDeleteRestrict();
            table.integer("quantity");
            table.decimal("unit_price", 10, 2);
            table.decimal("total_price", 10, 2);
        });
        log("Order items table created successfully");
    }
}