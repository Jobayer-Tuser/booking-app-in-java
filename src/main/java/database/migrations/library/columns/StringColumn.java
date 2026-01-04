package database.migrations.library.columns;

public class StringColumn extends Column<StringColumn> {
    private int length = 255;

    public StringColumn(String name, int length) {
        super(name);
        this.length = length;
    }

    public StringColumn(String name) {
        super(name);
    }

    @Override
    public String getDefinition() {
        return String.format("%s VARCHAR (%d)%s%s", name, length, (unique ? " UNIQUE" : "") ,(nullable ? " DEFAULT NULL" : " NOT NULL"));
    }
}