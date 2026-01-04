package database.migrations.library.columns;

public class DateTimeColumn extends Column<DateTimeColumn> {

    public DateTimeColumn(String name) {
        super(name);
    }

    @Override
    public String getDefinition() {
        return String.format("%s DATETIME(6)%s", name, (nullable ? " DEFAULT NULL" : " NOT NULL"));
    }
}
