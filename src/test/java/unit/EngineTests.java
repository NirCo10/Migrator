package unit;

import migrator.Application;
import migrator.engine.Engine;
import migrator.migration.Migration;
import migrator.migration.repository.MigrationRepositoryWrapper;
import migrator.migration.steps.V0001_CreatePeopleTable;
import migrator.migration.steps.V0002_CreateAddressTable;
import org.hibernate.JDBCException;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class EngineTests {

    private Engine engine;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Mock
    private MigrationRepositoryWrapper migrationRepositoryWrapper;

    @Before
    public void setUp() {
        initMocks(this);
        engine = new Engine(jdbcTemplate, migrationRepositoryWrapper);
//        expectedException = ExpectedException.none();
    }

    @After
    public void tearDown() {
        expectedException = ExpectedException.none();
    }

    @Test
    public void up_migrationToRunDoesNotExist_throwsException() {
        // Arrange
        when(migrationRepositoryWrapper.findByVersion(anyString())).thenReturn(null);
        expectedException.expectMessage(String.format("Could not find migration with version: %s", null));
        expectedException.expect(NoSuchElementException.class);

        // Act
        engine.up(null);
    }

    @Test
    public void up_noMigrationRan_theMigrationToRunIsExecuted() {
        // Arrange
        final boolean expectedUpResult = true;
        Migration migrationToExecute = new V0001_CreatePeopleTable();
        ArgumentCaptor<Migration> migrationArgumentCaptor = ArgumentCaptor.forClass(Migration.class);
        when(migrationRepositoryWrapper.findByVersion(anyString())).thenReturn(migrationToExecute);
        when(migrationRepositoryWrapper.findLastRanMigration()).thenReturn(null);

        // Act
        boolean actualUpResult = engine.up(matches("(\\d{4})"));

        // Assert
        verify(jdbcTemplate).execute(migrationToExecute.getUp());
        verify(migrationRepositoryWrapper, times(1)).save(migrationArgumentCaptor.capture());
        assertThat(migrationArgumentCaptor.getValue(), equalTo(migrationToExecute));
        assertThat(expectedUpResult, is(actualUpResult));
    }

    @Test
    public void up_migrationRanBefore_runSuccessfullyPreviousIsFalse() {
        // Arrange
        final boolean expectedUpResult = true;
        Migration prevMigration = new V0001_CreatePeopleTable();
        Migration migrationToRun = new V0002_CreateAddressTable();
        prevMigration.setRanLast(true);
        ArgumentCaptor<Migration> migrationArgumentCaptor = ArgumentCaptor.forClass(Migration.class);
        when(migrationRepositoryWrapper.findByVersion(anyString())).thenReturn(migrationToRun);
        when(migrationRepositoryWrapper.findLastRanMigration()).thenReturn(prevMigration);

        // Act
        boolean actualUpResult = engine.up(matches("(\\d{4})"));

        // Assert
        verify(jdbcTemplate).execute(migrationToRun.getUp());
        verify(migrationRepositoryWrapper, times(2)).save(migrationArgumentCaptor.capture());
        assertThat(migrationArgumentCaptor.getAllValues().get(0), equalTo(migrationToRun));
        assertThat(migrationArgumentCaptor.getAllValues().get(1), equalTo(prevMigration));
        assertThat(expectedUpResult, is(actualUpResult));
    }

    @Test
    public void up_jdbcFailsToExecute_getFalse() {
        // Arrange
        final boolean expectedRunResult = false;
        V0001_CreatePeopleTable toBeReturned = new V0001_CreatePeopleTable();
        doThrow(JDBCException.class).when(jdbcTemplate).execute(anyString());
        when(migrationRepositoryWrapper.findByVersion(anyString())).thenReturn(toBeReturned);
        when(migrationRepositoryWrapper.findLastRanMigration()).thenReturn(toBeReturned);

        // Act
        boolean actualUpResult = engine.up(matches("(\\d{4})"));

        // Assert
        assertThat(actualUpResult, is(expectedRunResult));
        verify(migrationRepositoryWrapper, times(0)).save(any(Migration.class));
    }
}
