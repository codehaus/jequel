package de.jexp.jequel.expression;

import de.jexp.jequel.util.ClassUtils;

/**
 * @author mh14 @ jexp.de
 * @since 24.10.2007 21:28:51 (c) 2007 jexp.de
 */
public class DelegatingFormat<T extends Format> implements Format {
    public static final String DEFAULT_SQL_FORMAT = "de.jexp.jequel.format.Sql92Format";

    private T format;

    public DelegatingFormat() {
        this(DEFAULT_SQL_FORMAT);
    }

    public DelegatingFormat(final String formatClassName) {
        final T format = (T) ClassUtils.newInstance(formatClassName);
        setFormat(format);
    }

    public void setFormat(final T format) {
        this.format = format;
    }

    public T getFormat() {
        return format;
    }

    public String formatAround(final String expressionString, final Expression<?> expression) {
        return getFormat().formatAround(expressionString, expression);
    }
}