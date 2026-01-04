package database.migrations.library.columns;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EnumColumn extends Column<EnumColumn> {
    private final List<String> options;

    public EnumColumn(String name, String... options) {
        super(name);
        this.options = Arrays.asList(options);
    }

    @Override
    public String getDefinition() {
        String allowed = options.stream()
                .map(opt -> "'" + opt + "'")
                .collect(Collectors.joining(", "));
        return String.format("%s ENUM(%s) %s %s", name, allowed, (nullable ? "" : "NOT NULL"), formatDefault());
    }
}