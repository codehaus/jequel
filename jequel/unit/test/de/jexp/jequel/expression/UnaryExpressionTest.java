package de.jexp.jequel.expression;

import static de.jexp.jequel.expression.Expressions.*;
import de.jexp.jequel.tables.TEST_TABLES;
import junit.framework.TestCase;

/**
 * @author mh14 @ jexp.de
 * @copyright (c) 2007 jexp.de
 * @since 18.10.2007 15:17:02
 */
public class UnaryExpressionTest extends TestCase {
    public void testMin() {
        assertEquals("min(1)", min(1).toString());
    }

    public void testMax() {
        assertEquals("max(NULL)", max(NULL).toString());
    }

    public void testCombined() {
        assertEquals("not(max(FALSE))", not(max(FALSE)).toString());
    }

    public void testCombinedField() {
        assertEquals("not(max(ARTICLE.OID))", not(max(TEST_TABLES.ARTICLE.OID)).toString());
    }
}