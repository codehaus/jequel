package de.jexp.jequel.expression;

import static de.jexp.jequel.expression.Expressions.*;
import junit.framework.TestCase;

/**
 * @author mh14 @ jexp.de
 * @copyright (c) 2007 jexp.de
 * @since 18.10.2007 15:17:02
 */
public class BooleanExpressionTest extends TestCase {
    public void testTrue() {
        assertEquals("TRUE", TRUE.toString());
    }

    public void testFalse() {
        assertEquals("FALSE", FALSE.toString());
    }

    public void testNotFalse() {
        assertEquals("not(FALSE)", not(FALSE).toString());
    }

}