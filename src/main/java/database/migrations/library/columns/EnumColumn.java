package database.migrations.library.columns;

import java.util.Arrays;

public class EnumColumn extends Column<EnumColumn> {
    private final String[] options;

    public EnumColumn(String name, String... options) {
        super(name);
        this.options = options;
    }

    @Override
    protected String sqlType() {
        String values = String.join(", ", Arrays.stream(options).map(o -> "'" + o + "'").toArray(String[]::new));
        return "ENUM(" + values + ")";
    }
}