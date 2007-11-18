package de.jexp.bricksandmortar.database;

import de.jexp.bricksandmortar.results.ListStepResult;

import java.util.List;
import java.util.Map;

/**
 * @author mh14 @ jexp.de
 * @since 15.11.2007 23:00:34 (c) 2007 jexp.de
 */
public class IbatisQueryStep extends IbatisStep<ListStepResult> {
    protected ListStepResult execute(final Map<String, ?> row, final int count) {
        //noinspection unchecked
        final List<Map<String, ?>> list =
                getSqlMapClientTemplate().queryForList(getQuery(), row);
        return new ListStepResult(createResultName(count), list);
    }
}
