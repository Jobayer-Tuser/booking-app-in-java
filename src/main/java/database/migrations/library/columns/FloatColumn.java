package database.migrations.library.columns;

public class FloatColumn extends Column<FloatColumn> {

    FloatColumn(String name) {
        super(name);
    }

    @Override
    public String getDefinition() {
        return String.format("%s FLOAT %s", name ,(nullable ? " DEFAULT NULL" : " NOT NULL"));
    }
}
