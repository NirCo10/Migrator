package unit;

import migrator.ddl.entities.Column;
import migrator.sql_generator.table.TableGenerator;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class TableGeneratorTests {

    private TableGenerator tableGenerator;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private List<Column> columns;
    private Column id;
    private String table;
    private String schema;

    @Before
    public void setUp() {
        tableGenerator = new TableGenerator();

        schema = "test";
        table = "people";
        id = new Column("id", "VARACHAR(9)");
        columns = Arrays.asList(new Column("first_name", "VARCHAR(250)"), new Column("last_name", "VARCHAR(250)"));
    }

    @Test
    public void createTableWithIdAndColumns() {
        // Arrange
        String columnsStr = columns.stream()
                .map(Column::toString)
                .collect(Collectors.joining(", ", "", ")"));
        String expectedSql = "CREATE TABLE " + schema + "." + table +
                " (" + id.toString() + " PRIMARY KEY, " + columnsStr;

        // Act
        String sql = tableGenerator.createTable(schema, table).withId(id).withColumns(columns).get();

        // Assert
        assertThat(sql, is(expectedSql));
    }

    @Test
    public void createTableWithColumnAndNoId() {
        // Arrange
        String columnsStr = columns.stream()
                .map(Column::toString)
                .collect(Collectors.joining(", ", " (", ")"));
        String expectedSql = "CREATE TABLE " + schema + "." + table + columnsStr;

        // Act
        String sql = tableGenerator.createTable(schema, table).withColumns(columns).get();

        // Assert
        assertThat(sql, is(expectedSql));
    }

    @Test
    public void tableNameIsNull_throws() {
        // Arrange
        expectedException.expect(NullPointerException.class);

        // Act
        String sql = tableGenerator.createTable(schema, null).withColumns(columns).get();
    }
}
