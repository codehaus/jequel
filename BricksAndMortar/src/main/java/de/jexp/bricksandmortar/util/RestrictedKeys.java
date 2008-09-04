package de.jexp.bricksandmortar.util;

import de.jexp.bricksandmortar.results.ListStepResult;

import java.util.*;

/**
 * @author mh14 @ jexp.de
 * @since 24.11.2007 01:52:14 (c) 2007 jexp.de
 */
public class RestrictedKeys implements Map<String, Object> {
    private final Map<String, Object> keys;
    private final Map<String, Object> values;

    public RestrictedKeys(final Map<String, Object> values, final String... keys) {
        this.values = values;
        if (!RestrictedKeyCollection.hasKeys(keys)) {
            this.keys = values;
        } else {
            this.keys = new HashMap<String, Object>(keys.length);
            for (final String key : keys) {
                this.keys.put(key, this.values.get(key));
            }
        }
    }

    public String toString() {
        return keys.toString() + "("+values.toString()+")";
    }

    public boolean equals(final Object o) {
        return o.getClass() == RestrictedKeys.class && keys.equals(((RestrictedKeys) o).keys);
    }

    public int hashCode() {
        return keys.hashCode();
    }

    public Map<String, Object> getValues() {
        return values;
    }

    public int size() {
        throw new UnsupportedOperationException();
    }

    public boolean isEmpty() {
        return false;
    }

    public boolean containsKey(final Object o) {
        throw new UnsupportedOperationException();
    }

    public boolean containsValue(final Object o) {
        return false;
    }

    public Object get(final Object o) {
        throw new UnsupportedOperationException();
    }

    public Object put(final String s, final Object o) {
        throw new UnsupportedOperationException();
    }

    public Object remove(final Object o) {
        throw new UnsupportedOperationException();
    }

    public void putAll(final Map<? extends String, ?> map) {
        throw new UnsupportedOperationException();
    }

    public void clear() {
        throw new UnsupportedOperationException();
    }

    public Set<String> keySet() {
        throw new UnsupportedOperationException();
    }

    public Collection<Object> values() {
        throw new UnsupportedOperationException();
    }

    public Set<Entry<String, Object>> entrySet() {
        throw new UnsupportedOperationException();
    }

}
