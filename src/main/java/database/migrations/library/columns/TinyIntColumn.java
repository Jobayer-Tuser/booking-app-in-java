package database.migrations.library.columns;

public class TinyIntColumn extends IntegerLikeColumn<TinyIntColumn> {
    public TinyIntColumn(String name) {
        super(name);
    }

    @Override
    protected String sqlType() {
        return "TINYINT" + unsignedSuffix();
    }
}
