package de.jexp.jequel.generator;

import static de.jexp.jequel.generator.GeneratorTestUtils.*;
import de.jexp.jequel.generator.data.SchemaMetaData;
import de.jexp.jequel.generator.data.TableMetaDataColumn;
import de.jexp.jequel.generator.processor.ResourceBundleMetaDataProcessor;
import de.jexp.jequel.generator.processor.TableRelationshipSchemaMetaDataProcessor;
import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author mh14 @ jexp.de
 * @copyright (c) 2007 jexp.de
 * @since 20.10.2007 01:38:16
 */
public class ResouceBundleEnhancerTest extends TestCase {

    private static final String TEST_REMARK = "Artikelnummer";
    private static final String TABLE_REMARK = "contains articles";

    public void testEnhanceByResourceBundle() {
        final SchemaMetaData schemaMetaData = GeneratorTestUtils.createTestSchemaMetaData();
        final Locale locale = Locale.GERMAN;
        addResourceBundleAndProcess(schemaMetaData, locale);
        assertEquals("has resbundle entry", TEST_REMARK + " (" + locale + ")",
                schemaMetaData.getTable(ARTICLE).getColumn(ARTICLE_NO).getRemark());

        assertEquals("table has resbundle entry", TABLE_TEST_REMARK + "\n" + TABLE_REMARK + " (" + locale + ")",
                schemaMetaData.getTable(ARTICLE).getRemark());
    }

    public void testEnhanceByResourceBundleWithRelationships() {
        final SchemaMetaData schemaMetaData = GeneratorTestUtils.createSchemaDataWithRelationship();
        final TableRelationshipSchemaMetaDataProcessor tableRelationshipSchemaMetaDataProcessor = new TableRelationshipSchemaMetaDataProcessor(schemaMetaData);
        tableRelationshipSchemaMetaDataProcessor.processMetaData();

        final Locale locale = Locale.ENGLISH;
        addResourceBundleAndProcess(schemaMetaData, locale);
        assertEquals("has resbundle entry", TEST_REMARK + " (" + locale + ")",
                schemaMetaData.getTable(ARTICLE).getColumn(ARTICLE_NO).getRemark());
        final TableMetaDataColumn articleColorArticleOidColumn = schemaMetaData.getTable(ARTICLE_COLOR).getColumn(ARTICLE_OID);
        assertTrue(articleColorArticleOidColumn.isForeignKey());
    }

    protected void addResourceBundleAndProcess(final SchemaMetaData schemaMetaData, final Locale locale) {
        final Map<String, String> testRemarks = new HashMap<String, String>();
        testRemarks.put(ARTICLE_NO, TEST_REMARK);
        testRemarks.put(ARTICLE, TABLE_REMARK);
        final ResourceBundle rs = new TestResourceBundle(testRemarks, locale);
        final ResourceBundleMetaDataProcessor metaDataProcessor = new ResourceBundleMetaDataProcessor(schemaMetaData);
        metaDataProcessor.setResourceBundle(rs);
        metaDataProcessor.processMetaData();
    }
}
