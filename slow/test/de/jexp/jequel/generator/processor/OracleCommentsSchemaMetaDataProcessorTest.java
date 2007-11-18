package de.jexp.jequel.generator.processor;

import de.jexp.jequel.generator.GeneratorTestUtils;
import de.jexp.jequel.generator.data.SchemaMetaData;
import de.jexp.jequel.generator.data.TableMetaData;
import de.jexp.jequel.generator.data.TableMetaDataColumn;
import junit.framework.TestCase;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class OracleCommentsSchemaMetaDataProcessorTest extends TestCase {
    private DataSource dataSource;
    private JdbcTemplate template;
    private static final String TEST_ARTICLE_NO = "Test ArticleNo";

    public void testOracleCommentsSchemaMetaDataProcessor() {
        final OracleCommentsSchemaMetaDataProcessor oracleCommentsSchemaMetaDataProcessor = new OracleCommentsSchemaMetaDataProcessor(GeneratorTestUtils.createTestSchemaMetaData());
        oracleCommentsSchemaMetaDataProcessor.setDataSource(dataSource);
        oracleCommentsSchemaMetaDataProcessor.processMetaData();
        final SchemaMetaData schemaMetaData = oracleCommentsSchemaMetaDataProcessor.getSchemaMetaData();
        final TableMetaData articleMetaData = schemaMetaData.getTable(GeneratorTestUtils.ARTICLE);
        final TableMetaDataColumn articleNoColumn = articleMetaData.getColumn(GeneratorTestUtils.ARTICLE_NO);
        assertEquals("comment on article_no", TEST_ARTICLE_NO, articleNoColumn.getRemark());
    }

    protected void setUp() throws Exception {
        dataSource = GeneratorTestUtils.createAndSetupHsqlDatasource();
        template = new JdbcTemplate(dataSource);
        template.execute("create table user_col_comments (table_name varchar(20), column_name varchar(20), comments varchar(50))");
        template.update("insert into user_col_comments values(?,?,?)", new Object[]{GeneratorTestUtils.ARTICLE, GeneratorTestUtils.ARTICLE_NO, TEST_ARTICLE_NO});
    }

    protected void tearDown() throws Exception {
        template.execute("drop table user_col_comments");
        GeneratorTestUtils.closeDatabase();
    }
}
