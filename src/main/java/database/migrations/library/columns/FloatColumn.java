package database.migrations.library.columns;

public class FloatColumn extends Column<FloatColumn> {
    FloatColumn(String name) {
        super(name);
    }

    @Override
    protected String sqlType() {
        return "FLOAT";
    }
}
