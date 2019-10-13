package migrator.ddl.entities;

import lombok.Getter;
import migrator.enums.ObjectType;

import java.util.List;

@Getter
public class Table extends BasicDdl {
    private List<Column> columns;

    public Table(String schemaName, String objectName, List<Column> columns) {
        super(schemaName, objectName, ObjectType.TABLE);
        this.columns = columns;
    }
}


