package database.migrations;

import database.migrations.library.Schema;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import java.sql.SQLException;


public class V8__CreateOrderItemsTable extends BaseJavaMigration {

    @Override
    public void migrate(Context context) throws SQLException {
        Schema.create("order_items", table -> {
            table.id();
            table.foreignId("product_id").constrained("products").onUpdateCascade().onDeleteRestrict();
            table.foreignId("order_id").constrained("orders").onUpdateCascade().onDeleteRestrict();
            table.integer("quantity");
            table.decimal("unit_price", 10, 2);
            table.decimal("total_price", 10, 2);
        }, context);

        IO.println("✓ orders Items table created successfully");
    }
}