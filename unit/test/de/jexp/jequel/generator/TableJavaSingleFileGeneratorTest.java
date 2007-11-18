package de.jexp.jequel.generator;

import static de.jexp.jequel.generator.GeneratorTestUtils.*;
import de.jexp.jequel.generator.data.SchemaMetaData;
import de.jexp.jequel.generator.data.TableMetaData;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

/**
 * @author mh14 @ jexp.de
 * @copyright (c) 2007 jexp.de
 * @since 19.10.2007 00:48:07
 */
public class TableJavaSingleFileGeneratorTest extends AbstractJavaFileGeneratorTest {
    protected static final String TEST_CLASS_ARTICLE_SINGLE = TEST_CLASS_ARTICLE.replace("final class", "final static class");
    protected static final String TEST_CLASS_ARTICLE_COLOR_SINGLE = TEST_CLASS_ARTICLE_COLOR.replace("final class", "final static class");

    public void testWriteFile() {
        setUpFileGeneratorWithArticleMetaData(SINGLE_FILE);
        final TableMetaData articleData = fileGenerationProcessor.getSchemaMetaData().getTable(ARTICLE);
        assertEquals(TEST_CLASS_ARTICLE_SINGLE, fileGenerationProcessor.createTableClassSource(articleData, SINGLE_FILE));
        final TableMetaData articleColorData = fileGenerationProcessor.getSchemaMetaData().getTable(ARTICLE_COLOR);
        assertEquals(TEST_CLASS_ARTICLE_COLOR_SINGLE, fileGenerationProcessor.createTableClassSource(articleColorData, SINGLE_FILE));
    }

    public void testCreateTableInstance() {
        setUpFileGeneratorWithArticleMetaData(SINGLE_FILE);
        final TableMetaData articleMetaData = fileGenerationProcessor.getSchemaMetaData().getTable(ARTICLE);
        assertEquals(TABLE_LINE_ARTICLE, fileGenerationProcessor.createTableInstanceVariable(articleMetaData));
        final TableMetaData articleColorMetaData = fileGenerationProcessor.getSchemaMetaData().getTable(ARTICLE_COLOR);
        assertEquals(TABLE_LINE_ARTICLE_COLOR, fileGenerationProcessor.createTableInstanceVariable(articleColorMetaData));
    }

    public void testConvertColumn() {
        setUpFileGeneratorWithArticleMetaData(SINGLE_FILE);
        final SchemaMetaData schemaMetaData = fileGenerationProcessor.getSchemaMetaData();
        final TableMetaData articleMetaData = schemaMetaData.getTable(ARTICLE);
        assertEquals("    public final Field OID = numeric().primaryKey();\n", createColumnSource(articleMetaData, OID));
        assertEquals("    public final Field NAME = string();\n", createColumnSource(articleMetaData, NAME));
        assertEquals("    public final Field ACTIVE = date();\n", createColumnSource(articleMetaData, ACTIVE));
        assertEquals("    public final Field ARTICLE_NO = integer();\n", createColumnSource(articleMetaData, ARTICLE_NO));

        final TableMetaData articleColorMetaData = schemaMetaData.getTable(ARTICLE_COLOR);
        assertEquals("    public final Field OID = numeric().primaryKey();\n", createColumnSource(articleColorMetaData, OID));
        assertEquals("    public final Field ARTICLE_COLOR_NO = string();\n", createColumnSource(articleColorMetaData, ARTICLE_COLOR_NO));
        assertEquals("    public final Field ARTICLE_OID = foreignKey(GEN_TEST_TABLES.ARTICLE.class,\"OID\");\n", createColumnSource(articleColorMetaData, ARTICLE_OID));
    }

    public void testCreateAllTablesSource() {
        setUpFileGeneratorWithArticleMetaData(SINGLE_FILE);
        final String allTablesSource = fileGenerationProcessor.createSchemaFileSource(TEST_PACKAGE_SINGLE, TEST_CLASS, SINGLE_FILE);
        assertSingleTestFileContent(allTablesSource, TEST_PACKAGE_SINGLE);
    }

    public void testCreateAndWriteFile() throws IOException {
        setUpFileGeneratorWithArticleMetaData(SINGLE_FILE);
        fileGenerationProcessor.setBasePath(TEST_PATH);
        fileGenerationProcessor.setJavaPackage(TEST_PACKAGE_SINGLE);
        fileGenerationProcessor.setJavaClassName(TEST_CLASS);
        fileGenerationProcessor.setMultiFile(SINGLE_FILE);
        fileGenerationProcessor.processMetaData();
        assertSingleTestFileContent(readTestFile(TEST_PATH, TEST_PACKAGE_SINGLE, TEST_CLASS), TEST_PACKAGE_SINGLE);
    }

    public void testReadFromDbCreateAndWriteFile() throws IOException {
        createTestMetaDataSourceFile(TEST_PACKAGE_SINGLE_DB, TEST_CLASS, SINGLE_FILE);
        assertSingleTestFileContent(readTestFile(TEST_PATH, TEST_PACKAGE_SINGLE_DB, TEST_CLASS), TEST_PACKAGE_SINGLE_DB);
    }

    protected void assertSingleTestFileContent(final String testFileSource, final String testPackage) {
        final int testPackageIdx = testFileSource.indexOf("package " + testPackage + ";");
        final int testClassIdx = testFileSource.indexOf("public abstract class " + GeneratorTestUtils.TEST_CLASS);
        final int testArticleClassIdx = testFileSource.indexOf(TEST_CLASS_ARTICLE_SINGLE);
        final int testArticleInstanceIdx = testFileSource.indexOf(TABLE_LINE_ARTICLE);

        final int testArticleColorClassIdx = testFileSource.indexOf(TEST_CLASS_ARTICLE_COLOR_SINGLE);
        final int testArticleColorInstanceIdx = testFileSource.indexOf(TABLE_LINE_ARTICLE_COLOR);

        final List<Integer> expectedOrder = Arrays.asList(testPackageIdx, testClassIdx,
                testArticleColorClassIdx, testArticleColorInstanceIdx, testArticleClassIdx, testArticleInstanceIdx);
        assertEquals("file part order " + testFileSource, expectedOrder.toString(), new TreeSet<Integer>(expectedOrder).toString());
    }
}
