package database.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class BaseMigration extends BaseJavaMigration {
    protected void execute(Context context, String sql) throws SQLException {
        try (PreparedStatement statement = context.getConnection().prepareStatement(sql)) {
            statement.execute();
        }
    }

    // Helper method for creating tables
    protected void createTable(Context context, String tableName, String columns) throws SQLException {
        String sql = String.format("CREATE IF NOT EXISTS TABLE %s (%s)", tableName, columns);
        execute(context, sql);
    }

    // Helper method for dropping tables
    protected void dropTable(Context context, String tableName) throws SQLException {
        String sql = String.format("DROP TABLE IF EXISTS %s", tableName);
        execute(context, sql);
    }

    // Helper method for adding columns
    protected void addColumn(Context context, String tableName, String columnDefinition) throws SQLException {
        String sql = String.format("ALTER TABLE %s ADD COLUMN %s", tableName, columnDefinition);
        execute(context, sql);
    }
}
