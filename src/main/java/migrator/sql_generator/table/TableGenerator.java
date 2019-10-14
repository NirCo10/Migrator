package migrator.sql_generator.table;

import migrator.ddl.entities.Column;

import java.util.List;

import static java.util.Objects.requireNonNull;

public class TableGenerator {

    private final TableObject table;

    public TableGenerator() {
        table = new TableObject();
    }

    public String get() {
        return table.toString();
    }

    public TableGenerator createTable(String schema, String tableName) {

        requireNonNull(tableName);

        this.table.setSchema(schema);
        this.table.setName(tableName);

        return this;
    }

    public TableGenerator withId(Column id) {

        this.table.setId(id);

        return this;
    }

    public TableGenerator withColumns(List<Column> columnsToCreate) {

        this.table.setColumns(columnsToCreate);

        return this;
    }
}
