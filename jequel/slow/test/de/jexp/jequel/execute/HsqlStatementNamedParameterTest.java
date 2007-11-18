package de.jexp.jequel.execute;

import de.jexp.jequel.expression.Expressions;
import de.jexp.jequel.generator.GeneratorTestUtils;
import de.jexp.jequel.sql.Sql;
import de.jexp.jequel.sql.SqlWrapper;
import static de.jexp.jequel.tables.TEST_TABLES.*;

public class HsqlStatementNamedParameterTest extends AbstractStatementTest {
    protected void setUp() throws Exception {
        dataSource = GeneratorTestUtils.createAndSetupHsqlDatasource();
        executableStatement = getArticleSql().executeOn(dataSource);
    }

    protected void tearDown() throws Exception {
        GeneratorTestUtils.closeDatabase();
    }

    public SqlWrapper getArticleSql() {
        final Sql sql = super.getArticleSql().toSql();
        sql.where().and(ARTICLE.OID.eq(Expressions.named("article_oid", 10)));
        return sql;
    }

    protected String getExpectedSql() {
        return super.getExpectedSql() + " and ARTICLE.OID = :article_oid";
    }

}