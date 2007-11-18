package de.jexp.jequel.generator.tables_db.multi;

import de.jexp.jequel.table.Field;
import de.jexp.jequel.table.BaseTable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * @author de.jexp.jequel.generator.JavaFileGenerationProcessor
 * @since So Nov 18 09:59:34 CET 2007
 * null 
 */

public final class ARTICLE_COLOR extends BaseTable<ARTICLE_COLOR> {
/** PK: ARTICLE_COLOR */
    public final Field OID = numeric().primaryKey();
    public final Field ARTICLE_COLOR_NO = string();
/** FK: ARTICLE; ARTICLE_TEST */
    public final Field ARTICLE_OID = foreignKey(GEN_TEST_TABLES.class,ARTICLE.class,"OID");
    { initFields(); }
}
