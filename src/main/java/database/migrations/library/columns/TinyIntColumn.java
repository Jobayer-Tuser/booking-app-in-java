package database.migrations.library.columns;

public class TinyIntColumn extends Column<TinyIntColumn> {
    private boolean unsigned = false;

    public TinyIntColumn(String name) {
        super(name);
    }

    public TinyIntColumn unsigned() {
        this.unsigned = true;
        return this;
    }

    @Override
    public String getDefinition() {
        return String.format("%s TINYINT %s%s", name, (unsigned ? "UNSIGNED" : "") ,(nullable ? " DEFAULT NULL" : " NOT NULL"));
    }
}
