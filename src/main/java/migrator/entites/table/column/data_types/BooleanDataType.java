package migrator.entites.table.column.data_types;

import migrator.entites.table.column.Column;

public class BooleanDataType extends DataType {
    public BooleanDataType(Column column) {
        super("BOOLEAN", column);
    }
}
