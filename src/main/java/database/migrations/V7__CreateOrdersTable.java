package database.migrations;

import database.migrations.library.Schema;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import java.sql.SQLException;


public class V7__CreateOrdersTable extends BaseJavaMigration {

    @Override
    public void migrate(Context context) throws SQLException {
        Schema.create("orders", table -> {
            table.id();
            table.foreignId("customer_id").constrained("users").onUpdateCascade().onDeleteRestrict();
            table.enumeration("status", "Confirmed", "Delivered", "Paid", "Pending").defaultValue("Pending");
            table.decimal("total_price", 10, 2);
            table.timeStamp("created_at");
        }, context);

        IO.println("✓ Orders table created successfully");
    }
}