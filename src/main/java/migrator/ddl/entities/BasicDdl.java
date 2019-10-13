package migrator.ddl.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import migrator.enums.ObjectType;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public abstract class BasicDdl implements Serializable {

    private String schemaName;
    private String objectName;
    private final ObjectType objectType;

    @Override
    public String toString() {
        return String.format("%s.%s", schemaName, objectName);
    }
}
