package database.migrations.library.columns;

public class TimeColumn extends Column<TimeColumn> {
    public TimeColumn(String name) {
        super(name);
    }

    @Override
    protected String sqlType() {
        return "TIME";
    }
}
