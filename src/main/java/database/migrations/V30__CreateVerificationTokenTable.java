package database.migrations;

import database.migrations.library.BaseMigration;
import database.migrations.library.Schema;

import java.sql.SQLException;

public class V30__CreateVerificationTokenTable extends BaseMigration {

    @Override
    protected void run(Schema schema) throws SQLException {
        schema.create("verification_token", table -> {
            table.id();
            table.foreignId("user_id").constrained();
            table.string("token");
            table.string("type", 32);
            table.datetime("created_at");
            table.datetime("expired_at");
            table.datetime("verified_at").nullable();
        });
        log("Verification token table created successfully");
    }
}