package database.migrations.library.columns;

import lombok.Setter;

public class ForeignIdColumn extends Column<ForeignIdColumn> {

    @Setter
    private String table;
    private boolean unsigned = true;
    private String referenceTable;
    private String referenceColumn = "id";
    private String onUpdateAction = null;
    private String onDeleteAction = null;


    public ForeignIdColumn(String name) {
        super(name);
    }

    public ForeignIdColumn constrained(String table) {
        this.referenceTable = table;
        return this;
    }

    public ForeignIdColumn constrained() {
        this.referenceTable = name.replace("_id", "s");
        return this;
    }

    public ForeignIdColumn references(String column) {
        this.referenceColumn = column;
        return this;
    }

    public ForeignIdColumn unsigned() {
        this.unsigned = true;
        return this;
    }

    public ForeignIdColumn onUpdateCascade() {
        this.onUpdateAction = "CASCADE";
        return this;
    }

    public ForeignIdColumn onDeleteCascade() {
        this.onDeleteAction = "CASCADE";
        return this;
    }

    public ForeignIdColumn onUpdateRestrict() {
        this.onUpdateAction = "RESTRICT";
        return this;
    }

    public ForeignIdColumn onDeleteRestrict() {
        this.onDeleteAction = "RESTRICT ";
        return this;
    }

    @Override
    public String getDefinition() {
        return String.format("%s BIGINT UNSIGNED %s", name, (nullable ? "DEFAULT NULL" : "NOT NULL"));
    }

    public String getConstraintSql() {
        if (referenceTable == null) return null;

        var builder = new StringBuilder(
                String.format("CONSTRAINT FK_%s_%s FOREIGN KEY (%s) REFERENCES %s(%s)",
                        table, name, name, referenceTable, referenceColumn));

        if (onUpdateAction != null) builder.append(" ON UPDATE ").append(onUpdateAction);

        if (onDeleteAction != null) builder.append(" ON DELETE ").append(onDeleteAction);

        return builder.toString();
    }
}
