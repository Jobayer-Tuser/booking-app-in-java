package database.migrations.library;

import org.flywaydb.core.api.migration.Context;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.function.Consumer;

public class Schema {

    public static void create(String tableName, Consumer<Blueprint> callback, Context context) throws SQLException {
        Blueprint table = new Blueprint(tableName);
        callback.accept(table);
        executeQuery(table.getSql(tableName), context);
    }

    public static void dropIfExists(String tableName, Context context) throws SQLException {
        executeQuery("DROP TABLE IF EXISTS " + tableName, context);
    }

    private static void executeQuery(String SqlQuery, Context context) throws SQLException {
        try (Statement statement = context.getConnection().createStatement()) {
            statement.execute(SqlQuery);
        }
    }

    public static void table(String tableName, Consumer<Blueprint> callback, Context context) throws SQLException {
        Blueprint table = new Blueprint(tableName);
        table.setAlterMode();
        callback.accept(table);

        context.getConnection().setAutoCommit(false);
        try (Statement statement = context.getConnection().createStatement()) {
            for (String sql : table.getAlterationSql(tableName)) {
                statement.execute(sql);
                context.getConnection().commit();
            }
        } catch (SQLException exception) {
            context.getConnection().rollback();
        }
    }
}