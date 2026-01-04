package database.migrations;

import database.migrations.library.Schema;
import org.flywaydb.core.api.migration.Context;

import java.sql.SQLException;


public class V3__CreateCategoriesTable extends BaseMigration {

    @Override
    public void migrate(Context context) throws SQLException {
        Schema.create("categories", table -> {
            table.id();
            table.foreignId("parent_category_id").constrained("categories").onUpdateCascade().onDeleteRestrict();
            table.string("name");
            table.timestamps();
        }, context);

        IO.println("✓ Category table created successfully");
    }
}