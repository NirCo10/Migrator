package migrator.sql_generator;

import migrator.ddl.entities.Column;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public class TableGenerator {

    private final StringBuilder sql;
    private boolean idCreated;

    public TableGenerator() {
        sql = new StringBuilder();
        idCreated = false;
    }

    public String get() {
        return this.sql.toString();
    }

    public TableGenerator createTable(String schema, String table) {

        requireNonNull(table);

        String createSql = "CREATE TABLE ";
        createSql += schema != null ? schema + "." + table : table;
        createSql += " ";

        this.sql.append(createSql);

        return this;
    }

    public TableGenerator withId(Column idColumn) {
        String column = idColumn.toString();
        column = "(" + column + " PRIMARY KEY, ";

        this.sql.append(column);
        idCreated = true;

        return this;
    }

    public TableGenerator andWithColumns(List<Column> columnsToCreate) throws Exception {

        if (!idCreated) {
            throw new Exception("id must be created before creating columns");
        }

        String columns = columnsToCreate.stream()
                .map(Column::toString)
                .collect(Collectors.joining(", ", "", ")"));

        this.sql.append(columns);

        return this;
    }
}
