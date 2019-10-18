package migrator.migration.repository;

import migrator.migration.Migration;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
class MigrationSpecifications {

    public Specification<Migration> didRunLastTrue() {
        return (root, query, cb) -> cb.isTrue(root.get("ranLast"));
    }

    public Specification<Migration> findByVersion(String version) {
        return (root, query, cb) -> cb.equal(root.get("version"), version);
    }
}
