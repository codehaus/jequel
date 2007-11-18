package de.jexp.jequel.expression;

/**
 * @author mh14 @ jexp.de
 * @since 25.10.2007 00:57:21 (c) 2007 jexp.de
 */
public interface AppendableExpression<T extends Expression> {
    void append(T... expressions);
}
