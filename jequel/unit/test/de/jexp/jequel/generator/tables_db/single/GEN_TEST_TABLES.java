package de.jexp.jequel.generator.tables_db.single;

import de.jexp.jequel.table.Field;
import de.jexp.jequel.table.BaseTable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * @author de.jexp.jequel.generator.JavaFileGenerationProcessor
 * @since Mo Nov 19 01:04:14 CET 2007
 * Generated from: -- database product: HSQL Database Engine 1.8.0
-- driver: org.hsqldb.jdbcDriver - HSQL Database Engine Driver 1.8.0
-- connection: jdbc:hsqldb:data/test
-- schema pattern: nullnull 
 */

public abstract class GEN_TEST_TABLES {
public final static class ARTICLE_COLOR extends BaseTable<ARTICLE_COLOR> {
/** PK: ARTICLE_COLOR */
    public final Field OID = numeric().primaryKey();
    public final Field ARTICLE_COLOR_NO = string();
/** FK: ARTICLE; ARTICLE_TEST */
    public final Field ARTICLE_OID = foreignKey(GEN_TEST_TABLES.ARTICLE.class,"OID");
    { initFields(); }
}

public final static ARTICLE_COLOR ARTICLE_COLOR = new ARTICLE_COLOR();
public final static class ARTICLE extends BaseTable<ARTICLE> {
/** PK: ARTICLE; ARTICLE_TEST */
    public final Field OID = numeric().primaryKey();
    public final Field NAME = string();
/** @deprecated */
    public final Field ACTIVE = date();
    public final Field ARTICLE_NO = integer();
    { initFields(); }
}
/** ARTICLE_TEST */

public final static ARTICLE ARTICLE = new ARTICLE();
}
