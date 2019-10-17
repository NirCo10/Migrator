package migrator.migration.repository;

import migrator.migration.Migration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
interface MigrationRepository extends JpaRepository<Migration, String>, JpaSpecificationExecutor<Migration> {
}
