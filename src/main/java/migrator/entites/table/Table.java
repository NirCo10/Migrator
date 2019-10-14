package migrator.entites.table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import migrator.entites.table.column.Column;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.logging.log4j.util.Strings.isBlank;

@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Table {

    //region fields

    private String name;
    private String schema;

    @Getter
    private List<Column> columns = new ArrayList<>();

    //endregion

    // region Override

    @Override
    public String toString() {
        String str = "CREATE TABLE ";
        str += schema == null ? name : schema + '.' + name;

        str += columns.stream()
                .map(Column::toString)
                .collect(Collectors.joining(", ", " (", ")"));

        str += ';';

        return str;
    }

    // endregion

    // region public methods

    public static Table create(String schema, String name) {

        if (isBlank(name)) {
            throw new RuntimeException("Table name can't be blank");
        }

        Table table = new Table();

        table.setSchema(schema);
        table.setName(name);

        return table;
    }

    public Column withIdColumn(String name) {
        return withColumn(name, true);
    }

    public Column withColumn(String name) {
        return withColumn(name, false);
    }

    // endregion

    // region private methods

    private Column withColumn(String name, boolean primaryKey) {
        return Column.create(this, name, primaryKey);
    }

    // endregion
}
