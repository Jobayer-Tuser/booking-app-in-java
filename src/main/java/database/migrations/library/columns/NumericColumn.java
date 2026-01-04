package database.migrations.library.columns;

public class NumericColumn extends Column<NumericColumn> {
    private int precision = 8;
    private int scale = 3;

    public NumericColumn(String name, int precision, int scale) {
        super(name);
        this.precision = precision;
        this.scale = scale;
    }

    public NumericColumn(String name) {
        super(name);
    }


    @Override
    public String getDefinition() {
        return String.format("%s NUMERIC(%d, %d)%s%s",
                name, precision, scale, (nullable ? " DEFAULT NULL" : " NOT NULL"), (defaultValue != null ? formatDefault() : ""));
    }
}