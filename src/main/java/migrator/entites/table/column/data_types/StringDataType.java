package migrator.entites.table.column.data_types;

import migrator.entites.table.column.Column;

import static java.lang.String.format;

public class StringDataType extends DataType {

    private final String SQL_TYPE_FORMAT = "%s(%d)";
    private final int DEFAULT_LENGTH = 250;

    private int length;

    public StringDataType(Column column, Integer length) {
        super("VARCHAR", column);
        this.length = length == null ? DEFAULT_LENGTH : length;
    }

    @Override
    public String toString() {
        return format(SQL_TYPE_FORMAT, super.toString(), length);
    }
}
