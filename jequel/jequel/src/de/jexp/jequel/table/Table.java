package de.jexp.jequel.table;

/**
 * @author mh14 @ jexp.de
 * @since 29.10.2007 23:24:06 (c) 2007 jexp.de
 */
public interface Table extends TablePart {
    Table resolve();

    Field getField(final String name);
}
