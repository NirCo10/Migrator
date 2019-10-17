package migrator.migration.steps;

import migrator.entites.table.Table;
import migrator.migration.Migration;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("0002")
public class V0002_CreateAddressTable extends Migration implements Step {

    public V0002_CreateAddressTable() {
        super("0002", "creates addresses table");
        up();
        down();
    }

    @Override
    public void up() {
        String sql = Table.create("app", "addresses")
                .withIdColumn("id").asString(9)
                .and().withColumn("address").notNullable().asString(50)
                .and().toString();

        this.up = sql;
    }

    @Override
    public void down() {
        this.down = "DROP TABLE app.addresses";
    }
}
