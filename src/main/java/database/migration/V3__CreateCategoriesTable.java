package database.migration;

import org.flywaydb.core.api.migration.Context;

import java.sql.SQLException;


public class V3__CreateCategoriesTable extends BaseMigration {

    @Override
    public void migrate(Context context) throws SQLException {
        createTable(context, "categories", """
            `id` tinyint NOT NULL AUTO_INCREMENT,
            `parent_category_id` tinyint DEFAULT NULL,
            `name` varchar(255) NOT NULL,
            PRIMARY KEY (`id`),
            KEY `categories_parent_category_id_foreign` (`parent_category_id`),
            CONSTRAINT `categories_parent_category_id_foreign` FOREIGN KEY (`parent_category_id`) REFERENCES `categories` (`id`) ON UPDATE CASCADE ON DELETE RESTRICT
        """);

        IO.println("✓ Category table created successfully");
    }
}