package database.migrations.library.columns;

public class NumericColumn extends Column<NumericColumn> {
    private final int precision;
    private final int scale;

    public NumericColumn(String name, int precision, int scale) {
        super(name);
        this.precision = precision;
        this.scale = scale;
    }

    public NumericColumn(String name) {
        this(name, 8, 3);
    }

    @Override
    protected String sqlType() {
        return String.format("NUMERIC(%d, %d)", precision, scale);
    }
}