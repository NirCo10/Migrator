package migrator.ddl;

import migrator.ddl.entities.Table;
import migrator.ddl.services.table.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class DdlController {

    private final TableService tableService;

//    private final String CREATE_TABLE_TEMPLATE = "CREATE TABLE %s (id SERIAL, first_name VARCHAR(255), last_name VARCHAR(255));";

    @Autowired
    public DdlController(TableService tableService) {
        this.tableService = tableService;
    }


    @RequestMapping("/")
    public String index() {
        return "Hello world";
    }

    @PostMapping("/test")
    public String test(@RequestBody HashMap map) {

        return map.values().stream().map(Object::toString).toString();
    }

    @PostMapping("/create/table")
    public String createTable(@RequestBody Table table) {
        return tableService.create(table);
    }
}