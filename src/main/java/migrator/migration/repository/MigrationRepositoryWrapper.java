package migrator.migration.repository;

import migrator.migration.Migration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MigrationRepositoryWrapper {

    private final MigrationRepository migrationRepository;

    @Autowired
    public MigrationRepositoryWrapper(MigrationRepository migrationRepository) {
        this.migrationRepository = migrationRepository;
    }

    public Migration findOne(String version) {
        Optional<Migration> optionalMigration = this.migrationRepository.findOne(MigrationSpecs.findByVersion(version));
        return optionalMigration.orElse(null);
    }

    public Migration save(Migration migration) {
        return this.migrationRepository.save(migration);
    }

    public List<Migration> save(List<Migration> migrations) {
        return this.migrationRepository.saveAll(migrations);
    }

    public Migration findLastRanMigration() {
        return this.migrationRepository.findOne(MigrationSpecs.didRunLastTrue()).orElse(null);
    }

    public List<Migration> findAll() {
        return this.migrationRepository.findAll();
    }
}
