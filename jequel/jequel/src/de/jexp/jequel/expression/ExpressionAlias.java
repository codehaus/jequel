package de.jexp.jequel.expression;

/**
 * @author mh14 @ jexp.de
 * @since 28.10.2007 21:23:14 (c) 2007 jexp.de
 */
public interface ExpressionAlias<T, E extends Expression<T>> extends Aliased<E>, Expression<T> {
}
