package de.jexp.bricksandmortar.transformation;

import de.jexp.bricksandmortar.results.ListStepResult;

import java.util.*;

/**
 * Created by mh14 on 07.07.2007 10:48:10
 */
public enum ListOperation {
    NOOP {
        public ListStepResult perform(final List<ListStepResult> sources) {
            return sources.get(0);
        }
    },
    TRANSPOSE {
        public ListStepResult perform(final List<ListStepResult> sources) {
            final Collection<Map<String, ?>> firstSource = sources.get(0).getResult();
            final Map<String, Collection<?>> result = new LinkedHashMap<String, Collection<?>>();
            for (final Map<String, ?> row : firstSource) {
                for (final Map.Entry<String, ?> column : row.entrySet()) {
                    Collection<Object> columnCollection = (Collection<Object>) result.get(column.getKey());
                    if (columnCollection == null) {
                        columnCollection = new ArrayList<Object>(firstSource.size());
                        result.put(column.getKey(), columnCollection);
                    }
                    columnCollection.add(column.getValue());
                }
            }
            final List<Map<String,?>> resultCollection = new ArrayList<Map<String, ?>>();
            resultCollection.add(result);
            return new ListStepResult(operationName(), resultCollection);
        }
    },
    UNION {
        public ListStepResult perform(final List<ListStepResult> sources) {
            int size = 0;
            for (final ListStepResult list : sources) {
                size += list.getResult().size();
            }
            final Set<Map<String, ?>> result = new HashSet<Map<String, ?>>(size);
            for (final ListStepResult list : sources) {
                result.addAll(list.getResult());
            }
            return new ListStepResult(operationName(), result);
        }},
    INTERSECTION {
        public ListStepResult perform(final List<ListStepResult> sources) {
            Set<Map<String, ?>> result = null;
            for (final ListStepResult list : sources) {
                if (result == null) {
                    result = new HashSet<Map<String, ?>>(list.getResult());
                } else
                    result.retainAll(list.getResult());
            }
            return new ListStepResult(operationName(), result);
        }
    },
    DIFFERENCE {
        public ListStepResult perform(final List<ListStepResult> sources) {
            Set<Map<String, ?>> result = null;
            for (final ListStepResult list : sources) {
                if (result == null) {
                    result = new HashSet<Map<String, ?>>(list.getResult());
                } else
                    result.removeAll(list.getResult());
            }
            return new ListStepResult(operationName(), result);
        }
    };

    protected String operationName() {
        return name().toLowerCase();
    }

    public abstract ListStepResult perform(final List<ListStepResult> sources);
}
