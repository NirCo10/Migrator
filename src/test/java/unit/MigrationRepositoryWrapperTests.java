package unit;

import migrator.Application;
import migrator.migration.Migration;
import migrator.migration.repository.MigrationRepositoryWrapper;
import migrator.migration.steps.V0001_CreatePeopleTable;
import migrator.migration.steps.V0002_CreateAddressTable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class MigrationRepositoryWrapperTests {

    @Autowired
    private MigrationRepositoryWrapper migrationRepositoryWrapper;

    private final V0001_CreatePeopleTable v0001_createPeopleTable = new V0001_CreatePeopleTable();
    private final V0002_CreateAddressTable v0002_createAddressTable = new V0002_CreateAddressTable();

    @Before
    public void setUp() {
        migrationRepositoryWrapper.save(asList(v0001_createPeopleTable, v0002_createAddressTable));
    }

    @After
    public void tearDown() {
        migrationRepositoryWrapper.deleteAll();
    }

    @Test
    public void findByVersion_versionExists_getExpectedMigration() {
        // Act
        Migration migration = migrationRepositoryWrapper.findByVersion(v0001_createPeopleTable.getVersion());

        // Assert
        assertEquals(v0001_createPeopleTable, migration);
    }

    @Test
    public void findByVersion_noVersion_getNull() {
        // Act
        Migration migration = migrationRepositoryWrapper.findByVersion("blah");

        // Assert
        assertNull(migration);
    }

    @Test
    public void findLastRanMigration_noMigrationExecuted_returnNull() {
        // Act
        Migration lastRanMigration = migrationRepositoryWrapper.findLastRanMigration();

        // Assert
        assertNull(lastRanMigration);
    }

    @Test
    public void findLastRanMigration_getExpected() {
        // Arrange
        v0002_createAddressTable.setRanLast(true);
        migrationRepositoryWrapper.save(v0002_createAddressTable);

        // Act
        Migration lastRanMigration = migrationRepositoryWrapper.findLastRanMigration();

        // Assert
        assertEquals(v0002_createAddressTable, lastRanMigration);
    }

    @Test
    public void findAll_getTwoSavedMigrations() {
        // Arrange
        int expectedSize = 2;

        // Act
        List<Migration> migrations = migrationRepositoryWrapper.findAll();

        // Assert
        assertThat(migrations.size(), is(expectedSize));
        assertThat(migrations, hasItems(v0001_createPeopleTable, v0002_createAddressTable));
    }

    @Test
    public void findOne_exists() {
        // Act
        Migration actualMigration = migrationRepositoryWrapper.findOne(v0001_createPeopleTable.getId());

        // Assert
        assertThat(actualMigration, equalTo(v0001_createPeopleTable));
    }

    @Test
    public void findOne_doesNotExist() {
        // Act
        Migration actualMigration = migrationRepositoryWrapper.findOne("v0001_createPeopleTable.getId()");

        // Assert
        assertThat(actualMigration, nullValue(Migration.class));
    }

    @Test
    public void save_validMigration() {
        // Arrange
        migrationRepositoryWrapper.delete(v0001_createPeopleTable);

        // Act
        Migration actualMigration = migrationRepositoryWrapper.save(v0001_createPeopleTable);
        Migration expectedMigration = migrationRepositoryWrapper.findOne(actualMigration.getId());

        // Assert
        assertEquals(expectedMigration, actualMigration);
    }

    @Test
    public void save_validMigrations() {
        // Arrange
        migrationRepositoryWrapper.deleteAll();

        // Act
        List<Migration> actualMigrations =
                migrationRepositoryWrapper.save(asList(v0001_createPeopleTable, v0002_createAddressTable));
        Migration v1 = migrationRepositoryWrapper.findByVersion(v0001_createPeopleTable.getVersion());
        Migration v2 = migrationRepositoryWrapper.findByVersion(v0002_createAddressTable.getVersion());

        // Assert
        assertThat(actualMigrations, hasItems(v1, v2));
    }

    @Test
    public void delete_byId_deletedSuccessfully() {
        // Arrange
        int migrationsExpectedSize = 1;

        // Act
        migrationRepositoryWrapper.delete(v0001_createPeopleTable.getId());

        // Assert
        assertThat(migrationRepositoryWrapper.findAll(), hasSize(migrationsExpectedSize));
        assertThat(migrationRepositoryWrapper.findOne(v0001_createPeopleTable.getId()), nullValue(Migration.class));
    }

    @Test
    public void delete_byObject_deletedSuccessfully() {
        // Arrange
        int migrationsExpectedSize = 1;

        // Act
        migrationRepositoryWrapper.delete(v0001_createPeopleTable);

        // Assert
        assertThat(migrationRepositoryWrapper.findAll(), hasSize(migrationsExpectedSize));
        assertThat(migrationRepositoryWrapper.findOne(v0001_createPeopleTable.getId()), nullValue(Migration.class));
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void delete_idNotExist() {
        migrationRepositoryWrapper.delete("blah");
    }
}
