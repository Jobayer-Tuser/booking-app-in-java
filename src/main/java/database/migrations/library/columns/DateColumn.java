package database.migrations.library.columns;

public class DateColumn extends Column<DateColumn> {

    public DateColumn(String name) {
        super(name);
    }

    @Override
    public String getDefinition() {
        return String.format("%s DATE%s", name, (nullable ? " DEFAULT NULL" : " NOT NULL"));
    }
}
