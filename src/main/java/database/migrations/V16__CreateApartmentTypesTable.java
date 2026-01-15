package database.migrations;

import database.migrations.library.Schema;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.hibernate.annotations.processing.SQL;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


public class V16__CreateApartmentTypesTable extends BaseJavaMigration {

    @Override
    public void migrate(Context context) throws SQLException, IOException {

        Schema.create("apartment_types", table -> {
            table.id();
            table.string("name");
            table.timestamps();
        }, context);
        IO.println("✓ Apartment Types table created successfully");
    }
}