package database.migrations.library.columns;

public abstract class Column<T extends Column<T>> {
    protected String name;
    protected boolean unique = false;
    protected boolean nullable = false;
    protected String afterColumn = null;
    protected Object defaultValue = null;

    public Column(String name) { this.name = name; }

    @SuppressWarnings("unchecked")
    public T nullable() {
        this.nullable = true;
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T after(String columnName) {
        this.afterColumn = columnName;
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

    public String afterColumn() {
        return afterColumn;
    }

    public abstract String getDefinition();
}