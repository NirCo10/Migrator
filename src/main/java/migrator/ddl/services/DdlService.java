package migrator.ddl.services;

import migrator.ddl.entities.BasicDdl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public abstract class DdlService {

    protected final JdbcTemplate jdbcTemplate;

    @Autowired
    protected DdlService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    protected void dropIfExists(BasicDdl basicDdl) {
        String query = String.format("DROP %s %s IF EXISTS;", basicDdl.getObjectType().toString(), basicDdl.toString());
        jdbcTemplate.execute(query);
    }
}
