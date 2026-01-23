package database.migrations;

import database.migrations.library.Schema;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import java.sql.SQLException;


public class V11__CreateProfilesTable extends BaseJavaMigration {

    @Override
    public void migrate(Context context) throws SQLException {

        Schema.create("profiles", table -> {
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
        }, context);


        IO.println("✓ Profiles table created successfully");
    }
}