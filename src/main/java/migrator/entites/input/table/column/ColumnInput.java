package migrator.entites.input.table.column;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ColumnInput {
    private String name;
    private String type;
    private boolean isId = false;

    public ColumnInput(String name, String type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public String toString() {
        return isId ? name + " SERIAL" : name + " " + type;
    }
}
