package de.jexp.jequel.expression;

/**
 * @author mh14 @ jexp.de
 * @since 30.10.2007 23:22:01 (c) 2007 jexp.de
 */
public interface Alias<T extends Expression<?>> {
    T as(String alias);

    T AS(String alias);
}
