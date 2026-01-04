package database.migrations.library.columns;

public abstract class Column<T extends Column<T>> {
    protected String name;
    protected Object defaultValue = null;
    protected boolean nullable = true;
    protected boolean unique = false;

    public Column(String name) { this.name = name; }

    @SuppressWarnings("unchecked")
    public T notNull() {
        this.nullable = false;
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T unique() {
        this.unique = true;
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T defaultValue(Object value) {
        this.defaultValue = value;
        return (T) this;
    }

    protected String formatDefault() {
        if (defaultValue == null) return "";
        if (defaultValue instanceof String) return "DEFAULT '" + defaultValue + "'";
        return "DEFAULT " + defaultValue;
    }

    public abstract String getDefinition();
}