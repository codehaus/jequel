package de.jexp.bricksandmortar.database;

import de.jexp.bricksandmortar.results.ListStepResult;
import de.jexp.bricksandmortar.database.UpcaseColumnMapRowMapper;

import java.util.List;
import java.util.Map;

/**
 * Created by mh14 on 05.07.2007 21:31:22
 */
public class NamedParameterQueryStep extends NamedParameterStep<ListStepResult> {
    protected ListStepResult execute(final Map<String, ?> row, final int count) {
        //noinspection unchecked
        final List<Map<String, ?>> result = getNamedJdbcTemplate().query(getQuery(), row, new UpcaseColumnMapRowMapper());
        return new ListStepResult(createResultName(count), result);
    }
}
