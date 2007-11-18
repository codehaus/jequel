package de.jexp.jequel.execute;

import static de.jexp.jequel.expression.Expressions.*;
import de.jexp.jequel.generator.GeneratorTestUtils;
import de.jexp.jequel.jdbc.beanprocessor.BeanRowMapper;
import de.jexp.jequel.sql.Sql;
import static de.jexp.jequel.sql.Sql.*;
import static de.jexp.jequel.tables.TEST_TABLES.*;
import junit.framework.TestCase;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Collection;

public class ParameterExampleTest extends TestCase {
    private DataSource dataSource;

    interface ArticleBean {
        int getArticleNo();

        String getName();
    }

    public void testParameterExample() {
        final Sql sql = Select(ARTICLE.NAME, ARTICLE.ARTICLE_NO)
                .from(ARTICLE)
                .where(ARTICLE.OID.in(named("article_oid"))).toSql();
        assertEquals("select ARTICLE.NAME, ARTICLE.ARTICLE_NO from ARTICLE where ARTICLE.OID in (:article_oid)", sql.toString());

        final Collection<String> articleDesc = sql.executeOn(dataSource)
                .withParams("article_oid", Arrays.asList(10, 11, 12))
                .mapBeans(new BeanRowMapper<ArticleBean, String>() {
                    public String mapBean(final ArticleBean bean) {
                        return bean.getArticleNo() + "/" + bean.getName();
                    }
                });
        assertEquals(1, articleDesc.size());
        assertEquals("12345/Foobar", articleDesc.iterator().next());
    }

    protected void setUp() throws Exception {
        dataSource = GeneratorTestUtils.createAndSetupHsqlDatasource();
    }

    protected void tearDown() throws Exception {
        GeneratorTestUtils.closeDatabase();
    }
}
