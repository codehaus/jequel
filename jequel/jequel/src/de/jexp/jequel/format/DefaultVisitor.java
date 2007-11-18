package de.jexp.jequel.format;

import de.jexp.jequel.expression.ExpressionVisitor;
import de.jexp.jequel.sql.SqlVisitor;
import de.jexp.jequel.table.TableVisitor;

/**
 * @author mh14 @ jexp.de
 * @since 06.11.2007 02:30:28 (c) 2007 jexp.de
 */
public interface DefaultVisitor<R> extends ExpressionVisitor<R>, SqlVisitor<R>, TableVisitor<R> {
}
