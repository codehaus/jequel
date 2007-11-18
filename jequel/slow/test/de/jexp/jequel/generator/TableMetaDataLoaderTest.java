package de.jexp.jequel.generator;

import de.jexp.jequel.generator.data.SchemaMetaData;
import de.jexp.jequel.generator.data.TableMetaData;
import junit.framework.TestCase;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.sql.Types;
import static java.sql.Types.*;
import java.util.Arrays;

/**
 * Created: mhu@salt-solutions.de 19.10.2007 11:37:34
 * (c) Salt Solutions GmbH 2006
 */
public class TableMetaDataLoaderTest extends TestCase {

    public void testLoadMetaData() {
        final SchemaMetaData schemaMetaData = GeneratorTestUtils.setUpLoaderWithDatabase();
        final TableMetaData articleData = schemaMetaData.getTable("article");
        assertNotNull(articleData);
        assertEquals(GeneratorTestUtils.ARTICLE, articleData.getName());
        assertEquals("columns", 4, articleData.getColumnCount());
        assertEquals(Arrays.asList(GeneratorTestUtils.OID, GeneratorTestUtils.NAME, GeneratorTestUtils.ACTIVE, GeneratorTestUtils.ARTICLE_NO).toString(), articleData.getColumnNames().toString());
        assertEquals(NUMERIC, articleData.getColumnType(GeneratorTestUtils.OID));
        assertEquals(VARCHAR, articleData.getColumnType(GeneratorTestUtils.NAME));
        assertEquals(DATE, articleData.getColumnType(GeneratorTestUtils.ACTIVE));
        assertEquals(INTEGER, articleData.getColumnType(GeneratorTestUtils.ARTICLE_NO));
    }

    public void testConvertTypes() {
        assertEquals(Integer.class, ColumnTypeHandler.getJavaType(Types.INTEGER));
        assertEquals(String.class, ColumnTypeHandler.getJavaType(Types.VARCHAR));
        assertEquals(BigDecimal.class, ColumnTypeHandler.getJavaType(Types.NUMERIC));
        assertEquals(Date.class, ColumnTypeHandler.getJavaType(Types.DATE));
        assertEquals(Timestamp.class, ColumnTypeHandler.getJavaType(Types.TIMESTAMP));
        assertEquals(Boolean.class, ColumnTypeHandler.getJavaType(Types.BIT));
        assertEquals(Object.class, ColumnTypeHandler.getJavaType(Types.OTHER));
    }

    public void testAllowedTableName() {
        assertFalse(ColumnTypeHandler.allowedTableName("TMP_"));
        assertFalse(ColumnTypeHandler.allowedTableName("TEST_"));
        assertFalse(ColumnTypeHandler.allowedTableName("DWH_"));
        assertFalse(ColumnTypeHandler.allowedTableName("SAV_"));
        assertFalse(ColumnTypeHandler.allowedTableName("SAVE_"));
        assertFalse(ColumnTypeHandler.allowedTableName("ABC$"));
    }

}
