package migrator.entites.output.table.column;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import migrator.entites.output.table.Table;

import static java.util.Objects.requireNonNull;
import static org.apache.logging.log4j.util.Strings.isBlank;

@Setter(AccessLevel.PRIVATE)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Column {

    // region fields

    private final Table table;
    private String name;
    private String type;
    private boolean primaryKey;
    private boolean nullable = true;
    private boolean unique = false;

    // endregion

    // region Override
    @Override
    public String toString() {
        String str = name + " " + type;
        str += primaryKey ? " PRIMARY KEY" : "";
        str += !nullable ? " NOT NULL" : "";
        str += unique ? " UNIQUE" : "";
        return str;
    }


    // endregion

    // region public methods

    public static Column create(Table table, String name, String type, boolean isPrimaryKey) {
        requireNonNull(table, "Column must be associated to a table!");

        if (isBlank(name)) {
            throw new RuntimeException("Column name can't be blank");
        }

        Column column = new Column(table);

        column.setName(name);
        column.setType(type);
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

    // endregion
}
