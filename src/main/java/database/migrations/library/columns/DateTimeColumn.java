package database.migrations.library.columns;

public class DateTimeColumn extends Column<DateTimeColumn> {
    public DateTimeColumn(String name) {
        super(name);
    }

    @Override
    protected String sqlType() {
        return "DATETIME(6)";
    }
}
