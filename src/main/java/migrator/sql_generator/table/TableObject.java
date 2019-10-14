package migrator.sql_generator.table;

import lombok.Data;
import migrator.ddl.entities.Column;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class TableObject {
    private String name;
    private String schema;
    private Column id;
    private List<Column> columns;

    @Override
    public String toString() {
        StringBuilder sql = new StringBuilder();

        sql.append(createStatement());
        sql.append(columnStatement());

        return sql.toString();
    }

    private String createStatement() {
        String create = "CREATE TABLE ";
        create += schema == null ? name : schema + '.' + name;

        return create;
    }

    private String columnStatement() {
        String columnsStr = "";
        String prefix = id == null ? " (" : "";

        if (id != null) {
            columnsStr = " (" + id.toString() + " PRIMARY KEY, ";
        }

        columnsStr += columns.stream()
                .map(Column::toString)
                .collect(Collectors.joining(", ", prefix, ")"));

        return columnsStr;
    }
}
