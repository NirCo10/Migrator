package migrator.migration.steps;

import migrator.entites.table.Table;
import migrator.migration.Migration;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("0001")
public class V0001_CreatePeopleTable extends Migration implements Step {

    public V0001_CreatePeopleTable() {
        super("0001", "creates people table");
        initMigration();
    }

    @Override
    public void up() {
        String sql = Table.create("app", "people")
                .withIdColumn("id").asString(9)
                .and().withColumn("first_name").notNullable().asString()
                .and().withColumn("last_name").asString()
                .and().withColumn("employed").asBoolean().withDefaultValue(true)
                .and().toString();

        this.up = sql;
    }

    @Override
    public void down() {
        String sql = "DROP TABLE app.people;";
        this.down = sql;
    }

    private void initMigration() {
        up();
        down();
    }
}
