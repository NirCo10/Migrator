package migrator.ddl.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Column {
    private String name;
    private String type;
    private boolean isId = false;

    public Column(String name, String type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public String toString() {
        return isId ? name + " SERIAL" : name + " " + type;
    }
}
