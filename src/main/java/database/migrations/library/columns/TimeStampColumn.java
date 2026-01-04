package database.migrations.library.columns;

public class TimeStampColumn extends Column<TimeStampColumn> {

    public TimeStampColumn(String name) {
        super(name);
    }

    @Override
    public String getDefinition() {
        return String.format("%s TIMESTAMP%s", name, (nullable ? " DEFAULT NULL" : " NOT NULL"));
    }
}
