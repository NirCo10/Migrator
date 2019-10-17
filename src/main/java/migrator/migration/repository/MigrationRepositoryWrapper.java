package migrator.migration.repository;

import migrator.migration.Migration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MigrationRepositoryWrapper {

    private final MigrationRepository migrationRepository;
    private final MigrationSpecifications migrationSpecifications;

    @Autowired
    public MigrationRepositoryWrapper(MigrationRepository migrationRepository, MigrationSpecifications migrationSpecifications) {
        this.migrationRepository = migrationRepository;
        this.migrationSpecifications = migrationSpecifications;
    }

    public Migration findOne(String id) {
        return migrationRepository.findById(id).orElse(null);
    }

    public List<Migration> findAll() {
        return migrationRepository.findAll();
    }

    public Migration findByVersion(String version) {
        Optional<Migration> optionalMigration = migrationRepository.findOne(migrationSpecifications.findByVersion(version));
        return optionalMigration.orElse(null);
    }

    public Migration findLastRanMigration() {
        return migrationRepository.findOne(migrationSpecifications.didRunLastTrue()).orElse(null);
    }

    public Migration save(Migration migration) {
        return migrationRepository.save(migration);
    }

    public List<Migration> save(List<Migration> migrations) {
        return migrationRepository.saveAll(migrations);
    }

    public void delete(Migration migration) {
        migrationRepository.delete(migration);
    }

    public void delete(String id) {
        migrationRepository.deleteById(id);
    }

    public void deleteAll() {
        migrationRepository.deleteAll();
    }
}
