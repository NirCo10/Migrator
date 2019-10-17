package migrator.migration.repository;

import migrator.migration.Migration;
import org.springframework.data.jpa.domain.Specification;

class MigrationSpecs {

    public static Specification<Migration> didRunLastTrue() {
        return (root, query, cb) -> cb.isTrue(root.get("ranLast"));
    }

    public static Specification<Migration> findByVersion(String version) {
        return (root, query, cb) -> cb.equal(root.get("version"), version);
    }
}
