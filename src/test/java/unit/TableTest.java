package unit;

import migrator.entites.table.Table;
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

    private String tableName;
    private String schema;
    private String idColumnName;
    private String firstName;
    private String lastName;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private int idColumnLength;

    @Before
    public void setUp() {
        schema = "test";
        tableName = "people";
        idColumnName = "id";
        firstName = "firstName";
        lastName = "lastName";
        idColumnLength = 9;
    }

    @Test
    public void createTableWithIdAndColumns() {
        // Arrange
        String expectedSql = "CREATE TABLE " + schema + "." + tableName + " (" +
                idColumnName + " VARCHAR(" + idColumnLength + ") PRIMARY KEY, " +
                firstName + " VARCHAR(250) NOT NULL UNIQUE, " +
                lastName + " VARCHAR(250));";

        // Act
        String sql = Table.create(schema, tableName)
                .withIdColumn(idColumnName).asString(idColumnLength)
                .and().withColumn(firstName).asString().unique().NotNullable()
                .and().withColumn(lastName).asString()
                .and().toString();

        // Assert
        assertThat(sql, is(expectedSql));
    }

    @Test
    public void createTableWithColumnsOnly() {
        // Arrange
        String expectedSql = "CREATE TABLE " + schema + "." + tableName + " (" +
                firstName + " VARCHAR(250) NOT NULL UNIQUE, " +
                lastName + " VARCHAR(250));";

        // Act
        String sql = Table.create(schema, tableName)
                .withColumn(firstName).asString().unique().NotNullable()
                .and().withColumn(lastName).asString()
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
        Table.create(schema, null).withIdColumn(idColumnName).asString(idColumnLength)
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
        Table.create(schema, Strings.EMPTY).withIdColumn(idColumnName).asString(idColumnLength)
                .and().withColumn(firstName).unique().NotNullable().asString()
                .and().withColumn(lastName).asString()
                .and().toString();
    }

    @Test
    public void createTableWithWhitespaceName_throws() {
        // Arrange + Assert
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Table name can't be blank");

        // Act
        Table.create(schema, "      ").withIdColumn(idColumnName).asString()
                .and().withColumn(firstName).unique().NotNullable().asString()
                .and().withColumn(lastName).asString()
                .and().toString();
    }

    @Test
    public void createTableWithBlankColumnName_throws() {
        // Arrange + Assert
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Column name can't be blank");

        // Act
        Table.create(schema, tableName).withIdColumn(idColumnName).asString()
                .and().withColumn(firstName).unique().NotNullable().asString()
                .and().withColumn(null).asString()
                .and().toString();
    }
}
