package de.jexp.bricksandmortar.transformation;

import de.jexp.bricksandmortar.results.ListStepResult;
import de.jexp.bricksandmortar.util.RestrictedKeys;

import java.util.*;

/**
 * Created by mh14 on 07.07.2007 10:48:10
 */
public enum ListOperation {
    NOOP {
        public ListStepResult perform(final Collection<ListStepResult> sources, final String... keys) {
            return sources.iterator().next();
        }
    },
    TRANSPOSE {
        public ListStepResult perform(final Collection<ListStepResult> sources, final String... keys) {
            final Collection<Map<String, ?>> firstSource = sources.iterator().next().getResult();
            final Map<String, Collection<?>> result = new LinkedHashMap<String, Collection<?>>();

            final Iterable<String> keyIterable = RestrictedKeys.hasKeys(keys) ? Arrays.asList(keys) : null;

            for (final Map<String, ?> row : firstSource) {
                for (final String key : keyIterable != null ? keyIterable : row.keySet()) {
                    final Object value = row.get(key);
                    Collection<Object> columnCollection = (Collection<Object>) result.get(key);
                    if (columnCollection == null) {
                        columnCollection = new ArrayList<Object>(firstSource.size());
                        result.put(key, columnCollection);
                    }
                    columnCollection.add(value);
                }
            }
            final List<Map<String, ?>> resultCollection = new ArrayList<Map<String, ?>>();
            resultCollection.add(result);
            return new ListStepResult(operationName(), resultCollection);
        }
    },
    UNION {
        public ListStepResult perform(final Collection<ListStepResult> sources, final String... keys) {
            int size = 0;
            for (final ListStepResult list : sources) {
                size += list.getResult().size();
            }
            final Set<Map<String, ?>> result = RestrictedKeys.createSet(size, keys);
            for (final ListStepResult list : sources) {
                result.addAll(list.getResult());
            }
            return new ListStepResult(operationName(), RestrictedKeys.resolve(result,keys));
        }},
    INTERSECTION {
        public ListStepResult perform(final Collection<ListStepResult> sources, final String... keys) {
            Set<Map<String, ?>> result = null;
            for (final ListStepResult list : sources) {
                if (result == null) {
                    result = RestrictedKeys.createSet(list, keys);
                } else
                    result.retainAll(list.getResult());
            }
            return new ListStepResult(operationName(), RestrictedKeys.resolve(result,keys));
        }
    },
    DIFFERENCE {
        public ListStepResult perform(final Collection<ListStepResult> sources, final String... keys) {
            Set<Map<String, ?>> result = null;
            for (final ListStepResult list : sources) {
                if (result == null) {
                    result = RestrictedKeys.createSet(list, keys);
                } else
                    result.removeAll(list.getResult());
            }
            return new ListStepResult(operationName(), RestrictedKeys.resolve(result,keys));
        }
    };

    protected String operationName() {
        return name().toLowerCase();
    }

    public abstract ListStepResult perform(final Collection<ListStepResult> sources, final String... keys);

}
