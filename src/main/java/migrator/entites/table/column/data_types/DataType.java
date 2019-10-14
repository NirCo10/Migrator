package migrator.entites.table.column.data_types;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import migrator.entites.table.column.Column;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class DataType {
    private final String sqlDataTypeName;
    private final Column column;

    @Override
    public String toString() {
        return sqlDataTypeName;
    }
}
