package de.jexp.bricksandmortar.transformation;

import de.jexp.bricksandmortar.results.ListStepResult;
import de.jexp.bricksandmortar.util.RestrictedKeys;
import de.jexp.bricksandmortar.util.RestrictedKeyCollection;

import java.util.*;

/**
 * Created by mh14 on 07.07.2007 10:48:10
 */
public enum ListOperation {
    NOOP {
        public ListStepResult perform(final Collection<ListStepResult> sources, final boolean distinct, final String... keys) {
            return sources.iterator().next();
        }
    },
    TRANSPOSE {
        public ListStepResult perform(final Collection<ListStepResult> sources, final boolean distinct, final String... keys) {
            final Collection<Map<String, ?>> firstSource = sources.iterator().next().getResult();
            final Map<String, Collection<?>> result = new LinkedHashMap<String, Collection<?>>();

            final Iterable<String> keyIterable = RestrictedKeyCollection.hasKeys(keys) ? Arrays.asList(keys) : null;

            for (final Map<String, ?> row : firstSource) {
                final int size = firstSource.size();
                for (final String key : keyIterable != null ? keyIterable : row.keySet()) {
                    final Object value = row.get(key);
                    Collection<Object> columnCollection = (Collection<Object>) result.get(key);
                    if (columnCollection == null) {
                        columnCollection = distinct ? new LinkedHashSet<Object>(size) : new ArrayList<Object>(size);
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
        public ListStepResult perform(final Collection<ListStepResult> sources, final boolean distinct, final String... keys) {
            int size = 0;
            for (final ListStepResult list : sources) {
                size += list.getResult().size();
            }
            final Collection<Map<String, ?>> result = new HashSet<Map<String, ?>>(RestrictedKeyCollection.createKeyBasedCollection(size, distinct, keys));
            for (final ListStepResult list : sources) {
                result.addAll(list.getResult());
            }
            return new ListStepResult(operationName(), RestrictedKeyCollection.resolve(result, keys));
        }},
    INTERSECTION {
        public ListStepResult perform(final Collection<ListStepResult> sources, final boolean distinct, final String... keys) {
            Collection<Map<String, ?>> result = null;
            for (final ListStepResult list : sources) {
                if (result == null) {
                    result = RestrictedKeyCollection.createSet(list, distinct,  keys);
                } else
                    result.retainAll(list.getResult());
            }
            return new ListStepResult(operationName(), RestrictedKeyCollection.resolve(result, keys));
        }
    },
    DIFFERENCE {
        public ListStepResult perform(final Collection<ListStepResult> sources, final boolean distinct, final String... keys) {
            Collection<Map<String, ?>> result = null;
            for (final ListStepResult list : sources) {
                if (result == null) {
                    result = RestrictedKeyCollection.createSet(list, distinct, keys);
                } else
                    result.removeAll(list.getResult());
            }
            return new ListStepResult(operationName(), RestrictedKeyCollection.resolve(result, keys));
        }
    },
    MERGE {
        public ListStepResult perform(final Collection<ListStepResult> sources, final boolean distinct, final String... keys) {
            Collection<Map<String, ?>> result = null;
            for (final ListStepResult list : sources) {
                if (result == null) {
                    result = RestrictedKeyCollection.createSet(list, distinct,  keys);
                } else {
                    final Collection<Map<String, ?>> otherList = RestrictedKeyCollection.createSet(list, distinct,  keys);
                    otherList.retainAll(result);
                    for (final Map<String, ?> row : result) {
                        final Map<String, Object> rowValues = ((RestrictedKeys) row).getValues();
                        final Iterator<Map<String, ?>> otherListIterator = otherList.iterator();
                        while (otherListIterator.hasNext()) {
                            final Map<String, ?> otherRow = otherListIterator.next();
                            if (otherRow.equals(row)) {
                                final RestrictedKeys otherRowValues = (RestrictedKeys) otherRow;
                                rowValues.putAll(otherRowValues.getValues());
                            }
                        }
                    }
                }
            }
            return new ListStepResult(operationName(), RestrictedKeyCollection.resolve(result, keys));
        }
    };

    protected String operationName() {
        return name().toLowerCase();
    }

    public abstract ListStepResult perform(final Collection<ListStepResult> sources, boolean distinct, final String... keys);
}
