package de.jexp.jequel;

import static de.jexp.jequel.expression.Expressions.*;
import static de.jexp.jequel.sql.Sql.*;
import de.jexp.jequel.tables.TEST_TABLES;
import static de.jexp.jequel.tables.TEST_TABLES.*;
import junit.framework.TestCase;

/**
 * @author mh14 @ jexp.de
 * @copyright (c) 2007 jexp.de
 * @since 20.10.2007 21:50:29
 */
public class SubSelectTest extends TestCase {
    public void testSubSelect() {
        final ARTICLE ARTICLE2 = ARTICLE.AS("article2");
        final SqlString sqlString = Select(ARTICLE.OID)
                .from(ARTICLE)
                .where(not(exits(
                        sub_select(e(1)).
                                from(ARTICLE2)
                                .where(ARTICLE2.OID.eq(ARTICLE.OID)
                                .and(ARTICLE2.ARTICLE_NO.is(NULL)))
                )));

        assertEquals("sub select",
                "select ARTICLE.OID from ARTICLE where not(exists(" +
                        "(select 1 from ARTICLE as ARTICLE2 where ARTICLE2.OID = ARTICLE.OID and ARTICLE2.ARTICLE_NO is NULL)" +
                        "))",
                sqlString.toString());

    }

    public void testSubSelect2() {
        final TEST_TABLES.ARTICLE_EAN ARTICLE_EAN2 = ARTICLE_EAN.AS("article_ean2");
        final SqlString sqlString = Select(ARTICLE_EAN.EAN)
                .from(ARTICLE_EAN)
                .where(ARTICLE_EAN.ARTICLE_OID.in(
                        Select(ARTICLE_EAN2.ARTICLE_OID)
                                .from(ARTICLE_EAN2)
                                .where(ARTICLE_EAN2.EAN.eq(e("1234567890123"))
                                .and(ARTICLE_EAN.ARTICLE_OID.eq(ARTICLE_EAN2.ARTICLE_OID)))));
        assertEquals("sub select 2",
                "select ARTICLE_EAN.EAN " +
                        "from ARTICLE_EAN " +
                        "where ARTICLE_EAN.ARTICLE_OID in " +
                        "(select ARTICLE_EAN2.ARTICLE_OID " +
                        "from ARTICLE_EAN as ARTICLE_EAN2 " +
                        "where ARTICLE_EAN2.EAN = '1234567890123' " +
                        "and ARTICLE_EAN.ARTICLE_OID = ARTICLE_EAN2.ARTICLE_OID)"
                , sqlString.toString());
    }
}
