package database.migrations;

import database.migrations.library.BaseMigration;
import database.migrations.library.Schema;

import java.sql.SQLException;

public class V11__CreateProfilesTable extends BaseMigration {

    @Override
    protected void run(Schema schema) throws SQLException {
        schema.create("profiles", table -> {
            table.id();
            table.foreignId("user_id").constrained("users").onUpdateCascade().onDeleteRestrict();
            table.string("gender", 32);
            table.date("birth_date");
            table.text("description");
            table.string("invoice_address");
            table.string("invoice_postcode");
            table.string("invoice_city");
            table.foreignId("invoice_country_id").constrained("country");
            table.foreignId("nationality_country_id").constrained("country");
            table.timeStamp("updated_at");
        });
        log("Profiles table created successfully");
    }
}