package database.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql. SQLException;

public abstract class BaseMigration extends BaseJavaMigration {
    protected void execute(Context context, String sql) throws SQLException {
        try (PreparedStatement statement = context.getConnection().prepareStatement(sql)) {
            statement.execute();
        }
    }

    // creating tables
    protected void createTable(Context context, String tableName, String columns) throws SQLException {
        execute(context, String.format("CREATE IF NOT EXISTS TABLE %s (%s)", tableName, columns));
    }

    // dropping tables
    protected void dropTable(Context context, String tableName) throws SQLException {
        execute(context, String.format("DROP TABLE IF EXISTS %s", tableName));
    }

    // adding columns
    protected void addColumn(Context context, String tableName, String columnDefinition) throws SQLException {
        execute(context, String.format("ALTER TABLE %s ADD COLUMN %s", tableName, columnDefinition));
    }

    // Drop column
    protected void dropColumn(Context context, String tableName, String columnName) throws SQLException {
        execute(context, String.format("ALTER TABLE %s DROP COLUMN %s", tableName, columnName));
    }

    // Rename column
    protected void renameColumn(Context context, String tableName, String oldName, String newName) throws SQLException {
        execute(context, String.format("ALTER TABLE %s RENAME COLUMN %s TO %s", tableName, oldName, newName));
    }

    // Modify column
    protected void modifyColumn(Context context, String tableName, String columnName, String newDefinition) throws SQLException {
        execute(context, String.format("ALTER TABLE %s MODIFY COLUMN %s %s", tableName, columnName, newDefinition));
    }

    // Create index
    protected void createIndex(Context context, String indexName, String tableName, String... columns) throws SQLException {
        String cols = String.join(", ", columns);
        execute(context, String.format("CREATE INDEX %s ON %s(%s)", indexName, tableName, cols));
    }

    // Drop index
    protected void dropIndex(Context context, String indexName, String tableName) throws SQLException {
        execute(context, String.format("DROP INDEX %s ON %s", indexName, tableName));
    }

    // Create unique index
    protected void createUniqueIndex(Context context, String indexName, String tableName, String... columns) throws SQLException {
        String cols = String.join(", ", columns);
        execute(context, String.format("CREATE UNIQUE INDEX %s ON %s(%s)", indexName, tableName, cols));
    }

    // Add foreign key
    protected void addForeignKey(Context context, String tableName, String constraintName,
                                 String column, String refTable, String refColumn) throws SQLException {
        String sql = String.format(
                "ALTER TABLE %s ADD CONSTRAINT %s FOREIGN KEY (%s) REFERENCES %s(%s)",
                tableName, constraintName, column, refTable, refColumn
        );
        execute(context, sql);
    }

    // Drop foreign key
    protected void dropForeignKey(Context context, String tableName, String constraintName) throws SQLException {
        execute(context, String.format("ALTER TABLE %s DROP FOREIGN KEY %s", tableName, constraintName));
    }

    // Check if table exists
    protected boolean tableExists(Context context, String tableName) throws SQLException {
        String sql = "SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = DATABASE() AND table_name = ?";
        try (PreparedStatement stmt = context.getConnection().prepareStatement(sql)) {
            stmt.setString(1, tableName);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }

    // Check if column exists
    protected boolean columnExists(Context context, String tableName, String columnName) throws SQLException {
        String sql = "SELECT COUNT(*) FROM information_schema.columns WHERE table_schema = DATABASE() AND table_name = ? AND column_name = ?";
        try (PreparedStatement stmt = context.getConnection().prepareStatement(sql)) {
            stmt.setString(1, tableName);
            stmt.setString(2, columnName);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }

    // Batch insert helper
    protected void batchInsert(Context context, String tableName, String[] columns, Object[][] values) throws SQLException {
        String placeholders = String.join(", ", "?".repeat(columns.length).split(""));
        String sql = String.format("INSERT INTO %s (%s) VALUES (%s)",
                tableName, String.join(", ", columns), placeholders);

        try (PreparedStatement stmt = context.getConnection().prepareStatement(sql)) {
            for (Object[] row : values) {
                for (int i = 0; i < row.length; i++) {
                    stmt.setObject(i + 1, row[i]);
                }
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }
}
