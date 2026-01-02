package database.migration;

import org.flywaydb.core.api.migration.Context;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ForeignKeyBuilder {

    private final Context context;
    private final StringBuilder queryBuilder = new StringBuilder();

    ForeignKeyBuilder(Context context, String tableName, String constraintName,
                      String column, String refTable, String refColumn) {
        this.context = context;
        queryBuilder.append(String.format("ALTER TABLE %s ADD CONSTRAINT %s FOREIGN KEY (%s) REFERENCES %s(%s)",
                tableName, constraintName, column, refTable, refColumn
        ));

    }

    public ForeignKeyBuilder onDeleteCascade() {
        queryBuilder.append(" ON DELETE CASCADE");
        return this;
    }

    public ForeignKeyBuilder onDeleteRestrict() {
        queryBuilder.append(" ON DELETE RESTRICT");
        return this;
    }

    public ForeignKeyBuilder onUpdateRestrict() {
        queryBuilder.append(" ON UPDATE RESTRICT");
        return this;
    }

    public ForeignKeyBuilder onUpdateCascade() {
        queryBuilder.append(" ON UPDATE CASCADE");
        return this;
    }

    public void execute() throws SQLException {
        try (PreparedStatement ps = context.getConnection().prepareStatement(queryBuilder.toString())) {
            ps.execute();
        }
    }
}
