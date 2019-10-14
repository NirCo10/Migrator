package migrator.entites.input.table;

import lombok.Getter;
import migrator.entites.input.BasicDdl;
import migrator.entites.input.table.column.ColumnInput;
import migrator.enums.ObjectType;

import java.util.List;

@Getter
public class TableInput extends BasicDdl {
    private List<ColumnInput> columnInputs;

    public TableInput(String schemaName, String objectName, List<ColumnInput> columnInputs) {
        super(schemaName, objectName, ObjectType.TABLE);
        this.columnInputs = columnInputs;
    }
}


