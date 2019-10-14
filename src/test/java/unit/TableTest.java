package unit;

import migrator.entites.input.table.column.ColumnInput;
import migrator.entites.output.table.Table;
import org.apache.logging.log4j.util.Strings;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class TableTest {

    private ColumnInput id;
    private String tableName;
    private String schema;
    private ColumnInput firstName;
    private ColumnInput lastName;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        schema = "test";
        tableName = "people";
        id = new ColumnInput("id", "VARCHAR(9)");
        firstName = new ColumnInput("first_name", "VARCHAR(250)");
        lastName = new ColumnInput("last_name", "VARCHAR(250)");
    }

    @Test
    public void createTableWithIdAndColumns() {
        // Arrange
        String expectedSql = "CREATE TABLE " + schema + "." + tableName + " (" +
                id.toString() + " PRIMARY KEY, " +
                firstName.toString() + " NOT NULL UNIQUE, " +
                lastName.toString() + ");";

        // Act
        String sql = Table.create(schema, tableName).withId(id)
                .and().withColumn(firstName).unique().NotNullable()
                .and().withColumn(lastName)
                .and().toString();

        // Assert
        assertThat(sql, is(expectedSql));
    }

    @Test
    public void createTableWithColumnsOnly() {
        // Arrange
        String expectedSql = "CREATE TABLE " + schema + "." + tableName + " (" +
                firstName.toString() + " NOT NULL UNIQUE, " +
                lastName.toString() + ");";

        // Act
        String sql = Table.create(schema, tableName)
                .withColumn(firstName).unique().NotNullable()
                .and().withColumn(lastName)
                .and().toString();

        // Assert
        assertThat(sql, is(expectedSql));
    }

    @Test
    public void createTableWithNullName_throws() {
        // Arrange + Assert
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Table name can't be blank");

        // Act
        Table.create(schema, null).withId(id)
                .and().withColumn(firstName).unique().NotNullable()
                .and().withColumn(lastName)
                .and().toString();
    }

    @Test
    public void createTableWithEmptyName_throws() {
        // Arrange + Assert
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Table name can't be blank");

        // Act
        Table.create(schema, Strings.EMPTY).withId(id)
                .and().withColumn(firstName).unique().NotNullable()
                .and().withColumn(lastName)
                .and().toString();
    }

    @Test
    public void createTableWithWhitespaceName_throws() {
        // Arrange + Assert
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Table name can't be blank");

        // Act
        Table.create(schema, "      ").withId(id)
                .and().withColumn(firstName).unique().NotNullable()
                .and().withColumn(lastName)
                .and().toString();
    }

    @Test
    public void createTableWithBlankColumnName_throws() {
        // Arrange + Assert
        ColumnInput columnInput = new ColumnInput("   ", "VARCHAR(10)");

        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Column name can't be blank");

        // Act
        Table.create(schema, tableName).withId(id)
                .and().withColumn(firstName).unique().NotNullable()
                .and().withColumn(columnInput)
                .and().toString();
    }
}
