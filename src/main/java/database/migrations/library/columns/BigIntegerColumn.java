package database.migrations.library.columns;

public class BigIntegerColumn extends Column<BigIntegerColumn> {
    private boolean unsigned = false;

    public BigIntegerColumn(String name) { super(name); }

    public BigIntegerColumn unsigned() {
        this.unsigned = true;
        return this;
    }

    @Override
    public String getDefinition() {
        return String.format("%s BIGINT %s %s", name, (unsigned ? "UNSIGNED" : ""), formatDefault());
    }
}