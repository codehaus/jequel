package de.jexp.bricksandmortar.util;

import junit.framework.TestCase;

import java.util.Arrays;
import java.util.Collection;

public class TextUtilsTest extends TestCase {
    public void testImplode() {
        assertEquals("implode a b c", "a b c", TextUtils.implode(Arrays.asList("a", "b", "c"), ' ').toString());
    }

    public void testCreateLine() {
        final StringBuilder sb = new StringBuilder();
        TextUtils.createLine(sb, Arrays.asList("a", "b", "c"));
        assertEquals("createLine a b c", "a\tb\tc"+TextUtils.NEWLINE, sb.toString());
    }

    public void testMakeTitles() {
        final Collection<String> result = TextUtils.makeTitles(Arrays.asList("a", "b", "c"));
        assertEquals("makeTitles a b c", Arrays.asList("A", "B", "C"), result);
    }
}
