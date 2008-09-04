package de.jexp.bricksandmortar.util;

import java.util.*;

/**
 * Created by mh14 on 06.07.2007 12:45:37
 */
public class TextUtils {
    public static final char TAB = '\t';
    public static final char NEWLINE = (char)Character.LINE_SEPARATOR;

    public static Collection<String> makeTitles(final Collection<String> titles) {
        final Collection<String> result=new ArrayList<String>(titles.size());
        for (final String title : titles) {
            result.add(title.toUpperCase());
        }
        return result;
    }

    public static void createLine(final StringBuilder sb, final Collection<?> values) {
        sb.append(implode(values, TAB));
        newLine(sb);
    }

    public static CharSequence implode(final Iterable<?> values, final char delim) {
        final StringBuilder sb = new StringBuilder(100);
        for (Iterator<?> it = values.iterator(); it.hasNext();) {
            sb.append(it.next());
            if (it.hasNext()) sb.append(delim);
        }
        return sb;
    }

    public static void tab(final StringBuilder sb) {
        sb.append(TAB);
    }

    public static void newLine(final StringBuilder sb) {
        sb.append(NEWLINE);
    }

    public static StringBuilder createText(final Collection<Map<String, ?>> list) {
        final StringBuilder sb=new StringBuilder(list.size()*30);
        boolean header=false;
        for (final Map<String,?> row : list) {
            if (!header) {
                header = true;
                createLine(sb, makeTitles(row.keySet()));
            }
            createLine(sb,row.values());

        }
        return sb;
    }

    public static CharSequence createText(final CharSequence text, final String header, final String footer) {
        final StringBuilder sb=new StringBuilder();
        if (header!=null) sb.append(header).append(NEWLINE);
        if (text!=null) sb.append(text);
        if (footer!=null) sb.append(NEWLINE).append(footer);
        return sb;
    }
}
