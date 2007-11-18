package de.jexp.bricksandmortar.util;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by mh14 on 06.07.2007 12:45:37
 */
public class TextUtils {
    public static final char TAB = '\t';
    public static final char NEWLINE = '\n';

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
}
