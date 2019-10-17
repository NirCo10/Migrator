package migrator.engine;

import migrator.enums.ExecutionPhase;
import migrator.migration.Migration;
import migrator.migration.repository.MigrationRepositoryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.NoSuchElementException;

import static java.lang.String.format;
import static migrator.enums.ExecutionPhase.DOWN;
import static migrator.enums.ExecutionPhase.UP;

@Component
public class Engine {

    private final JdbcTemplate jdbcTemplate;
    private final MigrationRepositoryWrapper migrationRepositoryWrapper;

    @Autowired
    public Engine(JdbcTemplate jdbcTemplate, MigrationRepositoryWrapper migrationRepositoryWrapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.migrationRepositoryWrapper = migrationRepositoryWrapper;
    }

    public boolean up(@NotNull String version) {
        Migration migration = migrationRepositoryWrapper.findOne(version);

        if (migration == null) {
            String message = format("Could not find migration with version: %s", version);
            throw new NoSuchElementException(message);
        }

        Migration lastRanMigration = migrationRepositoryWrapper.findLastRanMigration();

        boolean isSuccess = run(UP, migration);

        if (isSuccess && lastRanMigration != null && lastRanMigration.isRanLast()) {
            lastRanMigration.setRanLast(false);
            migrationRepositoryWrapper.save(lastRanMigration);
        }

        return isSuccess;
    }

    public boolean down(Migration migration) {
        return run(DOWN, migration);
    }

    private boolean run(ExecutionPhase executionPhase, Migration migration) {
        try {
            String sqlToExecute = executionPhase == UP ? migration.getUp() : migration.getDown();
            jdbcTemplate.execute(sqlToExecute);

            migration.setRanLast(true);

            migrationRepositoryWrapper.save(migration);

            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
    }
}