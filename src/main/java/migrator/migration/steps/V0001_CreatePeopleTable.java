package migrator.migration.steps;

import migrator.entites.table.Table;
import migrator.migration.Migration;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("0001")
public class V0001_CreatePeopleTable extends Migration {

    public V0001_CreatePeopleTable() {
        super("0001", "creates people table");
    }

    @Override
    protected String up() {
        String sql = Table.create("app", "people")
                .withIdColumn("id").asString(9)
                .and().withColumn("first_name").notNullable().asString()
                .and().withColumn("last_name").asString()
                .and().withColumn("employed").asBoolean().withDefaultValue(true)
                .and().toString();

        return sql;
    }

    @Override
    protected String down() {
        String sql = "DROP TABLE app.people;";
        return sql;
    }
}
