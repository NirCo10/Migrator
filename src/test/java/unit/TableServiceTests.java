//package unit;
//
//import migrator.sql_builder.entites.input.table.column.Column;
//import migrator.sql_builder.entites.input.table.Table;
//import migrator.ddl.services.table.TableService;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.ArgumentCaptor;
//import org.mockito.InOrder;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import static org.hamcrest.CoreMatchers.is;
//import static org.junit.Assert.assertThat;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.MockitoAnnotations.initMocks;
//
//@ActiveProfiles("test")
//@RunWith(SpringJUnit4ClassRunner.class)
//public class TableServiceTests {
//
//    private TableService tableService;
//
//    @Mock
//    private JdbcTemplate jdbcTemplate;
//    private Table table;
//
//    @Before
//    public void setUp() {
//        initMocks(this);
//        this.tableService = new TableService(jdbcTemplate);
//        List<Column> columns = Arrays.asList(new Column("id", "number", true), new Column("first_name", "VARCHAR(250)"));
//        this.table = new Table("testTable", "testSchema", columns);
//    }
//
//    @Test
//    public void dropIfExist_runsCorrectDropSql() {
//        // Arrange
//        String expectedDropSql = "DROP TABLE " + table.getSchemaName() + "." + table.getObjectName() + " IF EXISTS;";
//
//        // Act
//        this.tableService.dropIfExists(table);
//
//        // Assert
//        verify(jdbcTemplate).execute(expectedDropSql);
//    }
//
//    @Test
//    public void dropIfExist_dropsBeforeCreate() {
//        // Arrange
//        InOrder inOrder = Mockito.inOrder(jdbcTemplate);
//        String expectedDropSql = "DROP TABLE " + this.table.getSchemaName() + "." + this.table.getObjectName() + " IF EXISTS;";
//
//        String columnsStr = this.table.getColumns()
//                .stream()
//                .map(Column::toString)
//                .collect(Collectors.joining(", ", "(", ")"));
//
//        String expectedCreateSql = "CREATE " + this.table.getObjectType() + " " +
//                this.table.getSchemaName() + "." + this.table.getObjectName() +
//                " \n" + columnsStr;
//
//        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
//
//        // Act
//        this.tableService.create(this.table);
//
//        // Assert
//        verify(jdbcTemplate, times(2)).execute(stringArgumentCaptor.capture());
//        assertThat(stringArgumentCaptor.getAllValues().get(0), is(expectedDropSql));
//        assertThat(stringArgumentCaptor.getAllValues().get(1), is(expectedCreateSql));
//    }
//}
