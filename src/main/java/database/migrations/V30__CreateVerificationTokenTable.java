package database.migrations;

import database.migrations.library.Schema;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import java.sql.SQLException;


public class V30__CreateVerificationTokenTable extends BaseJavaMigration {

    @Override
    public void migrate(Context context) throws SQLException {

        Schema.create("verification_token", table -> {
            table.id();
            table.foreignId("user_id").constrained();
            table.string("token");
            table.string("type", 32);
            table.datetime("created_at");
            table.datetime("expired_at");
            table.datetime("verified_at").nullable();
        }, context);
        IO.println("✓ Verification token table created successfully");
    }
}