package de.jexp.jequel.generator;

import de.jexp.jequel.generator.data.SchemaMetaData;
import de.jexp.jequel.generator.data.TableMetaData;
import de.jexp.jequel.generator.data.TableMetaDataColumn;
import junit.framework.TestCase;

import javax.sql.DataSource;
import static java.sql.Types.*;
import java.util.Arrays;

/**
 * Created: mhu@salt-solutions.de 19.10.2007 11:37:34
 * (c) Salt Solutions GmbH 2006
 */
public class SchemaCrawlerTableMetaDataLoaderTest extends TestCase {
    private SchemaMetaData schemaMetaData;
    private DataSource dataSource;

    public void testLoadMetaData() {
        final TableMetaData articleData = checkMetaData(schemaMetaData, GeneratorTestUtils.ARTICLE,
                GeneratorTestUtils.OID, GeneratorTestUtils.NAME, GeneratorTestUtils.ACTIVE, GeneratorTestUtils.ARTICLE_NO);
        assertEquals(VARCHAR, articleData.getColumnType(GeneratorTestUtils.NAME));
        assertEquals(DATE, articleData.getColumnType(GeneratorTestUtils.ACTIVE));
        assertEquals(INTEGER, articleData.getColumnType(GeneratorTestUtils.ARTICLE_NO));
        assertTrue(articleData.getColumn(GeneratorTestUtils.OID).isPrimaryKey());

        final TableMetaData articleColorData = checkMetaData(schemaMetaData, GeneratorTestUtils.ARTICLE_COLOR,
                GeneratorTestUtils.OID, GeneratorTestUtils.ARTICLE_COLOR_NO, GeneratorTestUtils.ARTICLE_OID);
        assertEquals(VARCHAR, articleColorData.getColumnType(GeneratorTestUtils.ARTICLE_COLOR_NO));
        final TableMetaDataColumn articleOidColumn = articleColorData.getColumn(GeneratorTestUtils.ARTICLE_OID);
        assertTrue("foreign key", articleOidColumn.isForeignKey());
        assertEquals(articleData, articleOidColumn.getReferencedTable());
        assertEquals(articleData.getPrimaryKey(), articleOidColumn.getReferencedColumn());

    }

    protected TableMetaData checkMetaData(final SchemaMetaData schemaMetaData, final String tableName, final String... columnNames) {
        final TableMetaData tableMetaData = schemaMetaData.getTable(tableName);
        assertNotNull(tableMetaData);
        assertEquals(tableName, tableMetaData.getName());
        assertEquals("columns", columnNames.length, tableMetaData.getColumnCount());
        assertEquals(Arrays.asList(columnNames).toString(), tableMetaData.getColumnNames().toString());
        assertEquals(NUMERIC, tableMetaData.getColumnType(GeneratorTestUtils.OID));
        assertTrue(tableMetaData.getColumn(GeneratorTestUtils.OID).isPrimaryKey());
        return tableMetaData;
    }

    protected SchemaMetaData loadSchemaMetaData() {
        final SchemaCrawlerLoadSchemaMetaDataProcessor loadSchemaMetaDataProcessor = new SchemaCrawlerLoadSchemaMetaDataProcessor();
        dataSource = GeneratorTestUtils.createAndSetupHsqlDatasource();
        loadSchemaMetaDataProcessor.setHandleForeignKeys(true);
        loadSchemaMetaDataProcessor.setDataSource(dataSource);
        loadSchemaMetaDataProcessor.loadMetaData();
        return loadSchemaMetaDataProcessor.getSchemaMetaData();
    }

    protected void tearDown() throws Exception {
        GeneratorTestUtils.closeDatabase();
    }

    protected void setUp() throws Exception {
        schemaMetaData = loadSchemaMetaData();
    }
}