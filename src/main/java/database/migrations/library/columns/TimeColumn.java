package database.migrations.library.columns;

public class TimeColumn extends Column<TimeColumn> {

    public TimeColumn(String name) {
        super(name);
    }

    @Override
    public String getDefinition() {
        return String.format("%s TIME%s", name, (nullable ? " DEFAULT NULL" : " NOT NULL"));
    }
}
