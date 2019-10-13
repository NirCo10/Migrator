package migrator.ddl.services.table;

import migrator.ddl.entities.BasicDdl;
import migrator.ddl.entities.Column;
import migrator.ddl.entities.Table;
import migrator.ddl.services.DdlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TableService extends DdlService {

    @Autowired
    public TableService(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public void dropIfExists(BasicDdl basicDdl) {
        super.dropIfExists(basicDdl);
    }

    public String create(Table table) {

        dropIfExists(table);

        String columns = getColumns(table.getColumns());

        StringBuilder query = new StringBuilder();

        query.append(String.format("CREATE TABLE %s \n", table.toString()));
        query.append(columns);

        jdbcTemplate.execute(query.toString());

        return query.toString();
    }

    private String getColumns(List<Column> columns) {
        Stream<String> stringStream = columns.stream().map(Column::toString);

        String columnsStr = stringStream.collect(Collectors.joining(", ", "(", ")"));

        return columnsStr;
    }
}
