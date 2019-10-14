package migrator.entites.table.column;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import migrator.entites.table.Table;
import migrator.entites.table.column.data_types.BooleanDataType;
import migrator.entites.table.column.data_types.DataType;
import migrator.entites.table.column.data_types.StringDataType;

import static java.util.Objects.requireNonNull;
import static org.apache.logging.log4j.util.Strings.isBlank;
import static org.apache.logging.log4j.util.Strings.quote;

@Setter(AccessLevel.PRIVATE)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Column {

    // region fields

    private final Table table;
    private String name;
    private DataType type;
    private Object defaultValue;
    private boolean primaryKey;
    private boolean nullable = true;
    private boolean unique = false;

    // endregion

    // region Override
    @Override
    public String toString() {
        requireNonNull(this.type, "Column type not specified for column: " + name);

        String str = name + " " + type;
        str += primaryKey ? " PRIMARY KEY" : "";
        str += !nullable ? " NOT NULL" : "";
        str += unique ? " UNIQUE" : "";
        str += defaultValue != null ? " DEFAULT " + quote(defaultValue.toString()) : "";
        return str;
    }

    // endregion

    // region public methods

    public static Column create(Table table, String name, boolean isPrimaryKey) {
        requireNonNull(table, "Column must be associated to a table!");

        if (isBlank(name)) {
            throw new RuntimeException("Column name can't be blank");
        }

        Column column = new Column(table);

        column.setName(name);
        column.setPrimaryKey(isPrimaryKey);

        return column;
    }

    public Table and() {
        this.table.getColumns().add(this);
        return this.table;
    }

    public Column NotNullable() {
        this.setNullable(false);

        return this;
    }

    public Column unique() {
        this.setUnique(true);

        return this;
    }

    public <VAL> Column withDefaultValue(VAL value) {
        this.defaultValue = value;

        return this;
    }

    public Column asString() {
        return asString(null);
    }

    public Column asString(Integer length) {
        this.type = new StringDataType(this, length);

        return this;
    }

    public Column asBoolean() {
        this.type = new BooleanDataType(this);

        return this;
    }

    // endregion
}
