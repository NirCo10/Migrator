package migrator.ddl;

import migrator.engine.Engine;
import migrator.migration.Migration;
import migrator.migration.repository.MigrationRepositoryWrapper;
import migrator.migration.steps.V0001_CreatePeopleTable;
import migrator.migration.steps.V0002_CreateAddressTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.Arrays.asList;

@RestController
public class DdlController {

    private final MigrationRepositoryWrapper migrationRepositoryWrapper;
    private final Engine engine;
    private final V0001_CreatePeopleTable v0001_createPeopleTable = new V0001_CreatePeopleTable();
    private final V0002_CreateAddressTable v0002_createAddressTable = new V0002_CreateAddressTable();

    @Autowired
    public DdlController(MigrationRepositoryWrapper migrationRepository, Engine engine) {
        this.migrationRepositoryWrapper = migrationRepository;
        this.engine = engine;
        this.migrationRepositoryWrapper.save(asList(v0001_createPeopleTable, v0002_createAddressTable));
    }

    @RequestMapping("/")
    public String index() {
        return "Hello world";
    }

    @RequestMapping("/createPeopleTable")
    public boolean createPeopleTable() {
        return engine.up(v0001_createPeopleTable.getVersion());
    }

    @RequestMapping("/createAddresses")
    public boolean createAddresses() {
        return engine.up(v0002_createAddressTable.getVersion());
    }

    @RequestMapping("/migrations")
    public @ResponseBody List<Migration> migrations() {
        return this.migrationRepositoryWrapper.findAll();
    }
}