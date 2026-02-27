package database.migrations;

import database.migrations.library.BaseMigration;
import database.migrations.library.Schema;

import java.sql.SQLException;

public class V18__AddApartmentTypeToApartmentTable extends BaseMigration {

    @Override
    protected void run(Schema schema) throws SQLException {
        schema.table("apartments", table -> {
            table.foreignId("apartment_type_id").constrained("apartment_types").after("id");
            table.integer("size").unsigned().defaultValue(0);
        });
        log("Apartment type added to apartments table successfully");
    }
}