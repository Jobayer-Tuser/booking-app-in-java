package database.migrations;

import database.migrations.library.Schema;
import org.flywaydb.core.api.migration.Context;

import java.sql.SQLException;


public class V6__CreateCartItemsTable extends BaseMigration {

    @Override
    public void migrate(Context context) throws SQLException {
        Schema.create("cart_items", table -> {
            table.id();
            table.foreignId("cart_id").constrained("carts").onUpdateCascade().onDeleteRestrict();
            table.foreignId("product_id").constrained("products").onUpdateCascade().onDeleteRestrict();
            table.integer("quantity").notNull();
        }, context);

        IO.println("✓ CartItems table created successfully");
    }
}