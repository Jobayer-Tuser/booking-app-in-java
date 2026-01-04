package database.migrations.library.columns;

public class DoubleColumn extends Column<DoubleColumn> {

    public DoubleColumn(String name) {
        super(name);
    }

    @Override
    public String getDefinition() {
        return String.format("%s DOUBLE%s", name ,(nullable ? " DEFAULT NULL" : " NOT NULL"));
    }
}
