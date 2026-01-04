package database.migrations.library;

import database.migrations.library.columns.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Blueprint {

    private boolean isAlterMode = false;
    private List<String> columnsToDrop = new ArrayList<>();
    private final List<Column<?>> columns = new ArrayList<>();
    private final List<String> multiColumnUniques = new ArrayList<>();

    private String tableName;

    public Blueprint(String tableName) {
        this.tableName = tableName;
    }

    // Default constructor for backward compatibility if needed, or remove if not.
    // Given the plan implies full replacement, I will replace the implicit default
    // with the explicit on.
    // Wait, the original code had no constructor, so it was default.
    // I need to be careful about other usages. Schema creates it.

    public void id() {
        columns.add(new Column<BigIntegerColumn>("id") { // anonymous class call
            @Override
            public String getDefinition() {
                return "id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY";
            }
        });
    }

    public ForeignIdColumn foreignId(String name) {
        var col = new ForeignIdColumn(name);
        col.setTable(this.tableName);
        columns.add(col);
        return col;
    }

    public StringColumn string(String name, int length) {
        var col = new StringColumn(name, length);
        columns.add(col);
        return col;
    }

    public StringColumn string(String name) {
        var col = new StringColumn(name);
        columns.add(col);
        return col;
    }

    public DoubleColumn doubleColumn(String name) {
        var col = new DoubleColumn(name);
        columns.add(col);
        return col;
    }

    public NumericColumn numeric(String name, int precision, int scale) {
        var col = new NumericColumn(name, precision, scale);
        columns.add(col);
        return col;
    }

    public NumericColumn numeric(String name) {
        var col = new NumericColumn(name);
        columns.add(col);
        return col;
    }

    public IntegerColumn integer(String name) {
        var col = new IntegerColumn(name);
        columns.add(col);
        return col;
    }

    public BigIntegerColumn bigInteger(String name) {
        var col = new BigIntegerColumn(name);
        columns.add(col);
        return col;
    }

    public TimeStampColumn timeStamp(String name) {
        var col = new TimeStampColumn(name);
        columns.add(col);
        return col;
    }

    public DateTimeColumn datetime(String name) {
        var col = new DateTimeColumn(name);
        columns.add(col);
        return col;
    }

    public DateColumn date(String name) {
        var col = new DateColumn(name);
        columns.add(col);
        return col;
    }

    public TextColumn text(String name) {
        var col = new TextColumn(name);
        columns.add(col);
        return col;
    }

    public void unique(String... columnNames) {
        String cols = String.join(", ", columnNames);
        multiColumnUniques.add(String.format("UNIQUE (%s)", cols));
    }

    public DecimalColumn decimal(String name, int scale, int precision) {
        var col = new DecimalColumn(name, scale, precision);
        columns.add(col);
        return col;
    }

    public EnumColumn enumeration(String name, String... options) {
        EnumColumn col = new EnumColumn(name, options);
        columns.add(col);
        return col;
    }

    public String getSql(String tableName) {

        List<String> columnParts = new ArrayList<>();

        // 1. Column without Foreign Key Relation
        columns.forEach(c -> columnParts.add(c.getDefinition()));

        // 2. Merge Foreign Key Constraints
        columns.stream()
                .filter(ForeignIdColumn.class::isInstance)
                .map(ForeignIdColumn.class::cast)
                .map(ForeignIdColumn::getConstraintSql)
                .filter(Objects::nonNull)
                .forEach(columnParts::add);

        var finalQuery = String.join(", ", columnParts);
        return String.format("CREATE TABLE %s (%s)", tableName, finalQuery);
    }

    public void timestamps() {
        columns.add(new RawColumn("created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP"));
        columns.add(new RawColumn("updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP"));
    }

    public void dropColumn(String name) {
        columnsToDrop.add(name);
    }

    private static class RawColumn extends Column<RawColumn> {
        private final String definition;

        public RawColumn(String definition) {
            super("");
            this.definition = definition;
        }

        @Override
        public String getDefinition() {
            return definition;
        }
    }

    public List<String> getAlterationSql(String tableName) {
        List<String> sqlCommands = new ArrayList<>();

        // Handle Additions
        for (Column<?> col : columns) {
            sqlCommands.add(String.format("ALTER TABLE %s ADD %s", tableName, col.getDefinition()));
        }

        // Handle Drops
        for (String colName : columnsToDrop) {
            sqlCommands.add(String.format("ALTER TABLE %s DROP COLUMN %s", tableName, colName));
        }

        return sqlCommands;
    }
}
