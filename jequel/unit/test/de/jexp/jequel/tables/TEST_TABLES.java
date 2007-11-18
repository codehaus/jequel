package de.jexp.jequel.tables;

import de.jexp.jequel.table.BaseTable;
import de.jexp.jequel.table.Field;

/**
 * @author mh14 @ jexp.de
 * @copyright (c) 2007 jexp.de
 * this file is normally generated from database metadata
 * @since 17.10.2007 23:28:22
 */
public abstract class TEST_TABLES {
    public final static ARTICLE ARTICLE = new ARTICLE();

    public final static class ARTICLE extends BaseTable<ARTICLE> {
        /**
         * @deprecated
         */
        public Field<Integer> OID = integer().primaryKey();
        public Field NAME = string();
        public Field<Integer> ARTICLE_NO = integer();

        {
            initFields();
        }
    }

    /**
     * beim Kunden ist das der Artikel
     */
    public final static ARTICLE_COLOR ARTICLE_COLOR = new ARTICLE_COLOR();

    public final static class ARTICLE_COLOR extends BaseTable<ARTICLE_COLOR> {
        public Field<Integer> OID = integer().primaryKey();
        public Field ARTICLE_OID = foreignKey(ARTICLE.OID);

        {
            initFields();
        }
    }


    public final static ARTICLE_EAN ARTICLE_EAN = new ARTICLE_EAN();

    public final static class ARTICLE_EAN extends BaseTable<ARTICLE_EAN> {
        public Field OID = integer().primaryKey();
        public Field EAN = string();
        public Field ARTICLE_OID = foreignKey(ARTICLE.OID);

        {
            initFields();
        }

    }
}
