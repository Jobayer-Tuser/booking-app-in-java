package database.migrations;

import database.migrations.library.Schema;
import org.flywaydb.core.api.migration.Context;

import java.sql.SQLException;


public class V4__CreateProductsTable extends BaseMigration {

    @Override
    public void migrate(Context context) throws SQLException {
        Schema.create("products", table -> {
            table.id();
            table.foreignId("category_id").constrained("categories").onUpdateCascade().onDeleteRestrict();
            table.string("name").notNull();
            table.decimal("price", 10, 2);
            table.timestamps();
        }, context);

        IO.println("✓ Products table created successfully");
    }
}