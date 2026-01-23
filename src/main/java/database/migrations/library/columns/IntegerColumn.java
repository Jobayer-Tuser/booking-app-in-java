package database.migrations.library.columns;

public class IntegerColumn extends Column<IntegerColumn> {
    private boolean unsigned = false;

    public IntegerColumn(String name) { super(name); }

    public IntegerColumn unsigned() {
        this.unsigned = true;
        return this;
    }

    @Override
    public String getDefinition() {
        return String.format("%s INT %s %s", name, (unsigned ? "UNSIGNED" : ""), formatDefault());
    }
}