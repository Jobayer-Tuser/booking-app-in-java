package database.migrations.library;

import database.migrations.library.columns.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Blueprint {

    private boolean isAlterMode = false;
    private String tableName;
    private final List<Column<?>> columns = new ArrayList<>();
    private final List<String> columnsToDrop = new ArrayList<>();
    private final List<String> multiColumnUniquesConstraints = new ArrayList<>();
    private final List<String> foreignKeysToDrop = new ArrayList<>();

    public Blueprint() {}
    public Blueprint(String tableName) {
        this.tableName = tableName;
    }
    
    public void setAlterMode() {
        this.isAlterMode = true;
    }

    private <T extends Column<?>> T addColumn(T col) {
        columns.add(col);
        return col;
    }

    public void id() {
        addColumn(new Column<BigIntegerColumn>("id") {
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
        multiColumnUniquesConstraints.add(String.format("UNIQUE (%s)", cols));
    }

    public void softDeletes() {
        this.timeStamp("deleted_at");
    }

    public DecimalColumn decimal(String name, int scale, int precision) {
        return addColumn(new DecimalColumn(name, scale, precision));
    }

    public EnumColumn enumeration(String name, String... options) {
        return addColumn(new EnumColumn(name, options));
    }

    public String getSql(String tableName) {

        List<String> createQuery = new ArrayList<>();
        
        columns.forEach(c -> createQuery.add(c.getDefinition()));
        
        columns.stream()
                .filter(ForeignIdColumn.class::isInstance)
                .map(ForeignIdColumn.class::cast)
                .map(ForeignIdColumn::getConstraintSql)
                .filter(Objects::nonNull)
                .forEach(createQuery::add);

        createQuery.addAll(multiColumnUniquesConstraints);

        var finalQuery = String.join(", ", createQuery);
        return String.format("CREATE TABLE %s (%s)", tableName, finalQuery);
    }

    public void timestamps() {
        columns.add(new RawColumn("created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP"));
        columns.add(new RawColumn("updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP"));
    }

    public void dropColumn(String name) {
        columnsToDrop.add(name);
    }

    public void dropForeign(String columnName) {
        String constraintName = String.format("FK_%s_%s", tableName, columnName);
        foreignKeysToDrop.add(constraintName);
    }

    public List<String> getAlterationSql(String tableName) {
        List<String> alterQuery = new ArrayList<>();

        getDropColumnQuery(tableName, alterQuery);
        getAlterTableQuery(tableName, alterQuery);

        return alterQuery;
    }

    private void getAlterTableQuery(String tableName, List<String> alterQuery) {
        columns.forEach(column -> {
            var sql = new StringBuilder();

            sql.append(String.format("ALTER TABLE %s ADD %s", tableName, column.getDefinition()));

            if (column.afterColumn() != null) {
                sql.append(" AFTER ").append(column.afterColumn());
            }

            alterQuery.add(sql.toString());

            if (column instanceof ForeignIdColumn foreignIdCol) {
                String rule = foreignIdCol.getConstraintSql();
                if ( rule != null ) {
                    alterQuery.add(String.format("ALTER TABLE %s ADD %s", tableName, rule));
                }
            }
        });
    }

    private void getDropColumnQuery(String tableName, List<String> alterQuery) {
        foreignKeysToDrop.forEach(constraintName
                -> alterQuery.add(String.format("ALTER TABLE %s DROP FOREIGN KEY %s", tableName, constraintName)));

        columnsToDrop.forEach(colName ->
                alterQuery.add(String.format("ALTER TABLE %s DROP COLUMN %s", tableName, colName)));
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
}
