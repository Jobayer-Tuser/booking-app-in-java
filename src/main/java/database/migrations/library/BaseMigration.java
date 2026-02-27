package database.migrations.library;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import java.sql.SQLException;

public abstract class BaseMigration extends BaseJavaMigration {

    @Override
    public final void migrate(Context context) throws SQLException {
        run(new Schema(context));
    }

    protected abstract void run(Schema schema) throws SQLException;

    protected void log(String message) {
        System.out.println("✓ " + message);
    }
}
