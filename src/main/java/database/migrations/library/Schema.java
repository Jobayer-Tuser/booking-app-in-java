package database.migrations.library;

import org.flywaydb.core.api.migration.Context;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.function.Consumer;

public class Schema {

    public static void create(String tableName, Consumer<Blueprint> callback, Context context) throws SQLException {
        Blueprint table = new Blueprint(tableName);
        callback.accept(table);

        String sql = table.getSql(tableName);
        IO.println(sql);
        try (Statement statement = context.getConnection().createStatement()) {
            statement.execute(sql);
        }
    }

    public static void table(String tableName, Consumer<Blueprint> callback, Context context) throws Exception {
        Blueprint table = new Blueprint(tableName);

        callback.accept(table);

        try (Statement statement = context.getConnection().createStatement()) {
            for (String sql : table.getAlterationSql(tableName)) {
                statement.execute(sql);
            }
        }
    }
}