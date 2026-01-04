package database.migrations.library.columns;

public class DecimalColumn extends Column<DecimalColumn> {
    private int precision = 10;
    private int scale = 2;

    public DecimalColumn(String name, int precision, int scale) {
        super(name);
        this.precision = precision;
        this.scale = scale;
    }

    @Override
    public String getDefinition() {
        return String.format("%s DECIMAL(%d, %d)%s%s", name, precision, scale, (nullable ? " DEFAULT NULL" : " NOT NULL"), formatDefault());
    }
}