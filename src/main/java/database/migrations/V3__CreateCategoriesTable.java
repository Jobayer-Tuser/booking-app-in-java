package database.migrations;

import database.migrations.library.BaseMigration;
import database.migrations.library.Schema;

import java.sql.SQLException;

public class V3__CreateCategoriesTable extends BaseMigration {

    @Override
    protected void run(Schema schema) throws SQLException {
        schema.create("categories", table -> {
            table.id();
            table.foreignId("parent_category_id").constrained("categories").onUpdateCascade().onDeleteRestrict();
            table.string("name");
            table.timestamps();
        });
        log("Category table created successfully");
    }
}