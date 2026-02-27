package database.migrations;

import database.migrations.library.BaseMigration;
import database.migrations.library.Schema;

import java.sql.SQLException;

public class V7__CreateOrdersTable extends BaseMigration {

    @Override
    protected void run(Schema schema) throws SQLException {
        schema.create("orders", table -> {
            table.id();
            table.foreignId("customer_id").constrained("users").onUpdateCascade().onDeleteRestrict();
            table.enumeration("status", "Confirmed", "Delivered", "Paid", "Pending").defaultValue("Pending");
            table.decimal("total_price", 10, 2);
            table.timeStamp("created_at");
        });
        log("Orders table created successfully");
    }
}