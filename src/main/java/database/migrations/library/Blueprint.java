package database.migrations.library;

import database.migrations.library.columns.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Blueprint {

    private boolean isAlterMode = false;
    private String tableName;
    private final List<String> columnsToDrop = new ArrayList<>();
    private final List<Column<?>> columns = new ArrayList<>();
    private final List<String> multiColumnUniques = new ArrayList<>();

    public Blueprint() {}
    public Blueprint(String tableName) {
        this.tableName = tableName;
    }

    private <T extends Column<?>> T addColumn(T col) {
        columns.add(col);
        return col;
    }

    public void id() {
        addColumn(new Column<BigIntegerColumn>("id") { // anonymous class call
            @Override
            public String getDefinition() {
                return "id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY";
            }
        });
    }

    public ForeignIdColumn foreignId(String name) {
        var col = new ForeignIdColumn(name);
        col.setTable(this.tableName);
        return addColumn(col);
    }

    public StringColumn string(String name, int length) {
        return addColumn(new StringColumn(name, length));
    }

    public StringColumn string(String name) {
        return addColumn(new StringColumn(name));
    }

    public DoubleColumn doubleColumn(String name) {
        return addColumn(new DoubleColumn(name));
    }

    public NumericColumn numeric(String name, int precision, int scale) {
        return addColumn(new NumericColumn(name, precision, scale));
    }

    public NumericColumn numeric(String name) {
        return addColumn(new NumericColumn(name));
    }

    public IntegerColumn integer(String name) {
        return addColumn(new IntegerColumn(name));
    }

    public BigIntegerColumn bigInteger(String name) {
        return addColumn(new BigIntegerColumn(name));
    }

    public TimeStampColumn timeStamp(String name) {
        return addColumn(new TimeStampColumn(name));
    }

    public DateTimeColumn datetime(String name) {
        return addColumn(new DateTimeColumn(name));
    }

    public DateColumn date(String name) {
        return addColumn(new DateColumn(name));
    }

    public TextColumn text(String name) {
        return addColumn(new TextColumn(name));
    }

    public void unique(String... columnNames) {
        String cols = String.join(", ", columnNames);
        multiColumnUniques.add(String.format("UNIQUE (%s)", cols));
    }

    public DecimalColumn decimal(String name, int scale, int precision) {
        return addColumn(new DecimalColumn(name, scale, precision));
    }

    public EnumColumn enumeration(String name, String... options) {
        return addColumn(new EnumColumn(name, options));
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

        // 3. Add Multi-column Unique Constraint
        columnParts.addAll(multiColumnUniques);

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
