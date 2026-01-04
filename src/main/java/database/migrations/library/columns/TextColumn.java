package database.migrations.library.columns;

public class TextColumn extends Column<TextColumn> {

    public TextColumn(String name) {
        super(name);
    }

    @Override
    public String getDefinition() {
        return String.format("%s TEXT%s", name, (nullable ? " DEFAULT NULL" : " NOT NULL"));
    }
}
