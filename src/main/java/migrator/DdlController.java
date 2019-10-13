package migrator;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DdlController {

    @RequestMapping("/")
    public String index() {
        return "Hello world";
    }
}