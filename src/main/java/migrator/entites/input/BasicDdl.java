package migrator.entites.input;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import migrator.enums.ObjectType;

import java.io.Serializable;

//@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public abstract class BasicDdl implements Serializable {

    private String schema;
    private String name;
    private final ObjectType objectType;

    @Override
    public String toString() {
        return String.format("%s.%s", schema, name);
    }
}
