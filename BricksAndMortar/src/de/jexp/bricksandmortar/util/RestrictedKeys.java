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
        if (keys == null || keys.length == 0) {
            this.keys = values;
        } else {
            this.keys = new HashMap<String, Object>(keys.length);
            for (final String key : keys) {
                this.keys.put(key, this.values.get(key));
            }
        }
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

    public static Collection<Map<String, ?>> resolve(final Set<Map<String, ?>> result, final String[] keys) {
        if (!hasKeys(keys)) return result;
        final Collection<Map<String, ?>> values=new ArrayList<Map<String, ?>>(result.size());
        for (final Map<String, ?> value : result) {
            if (value instanceof RestrictedKeys) {
                values.add(((RestrictedKeys)value).getValues());
            } else values.add(value);
        }
        return values;
    }

    public static boolean hasKeys(final String... keys) {
        return keys != null && keys.length>0;
    }

    public static Set<Map<String, ?>> createSet(final int size, final String[] keys) {
        if (!hasKeys(keys))
            return new HashSet<Map<String, ?>>(size);
        return new RestrictedKeyMapSet(size, keys);
    }

    public static Set<Map<String, ?>> createSet(final ListStepResult list, final String[] keys) {
        final Collection<Map<String, ?>> rows = list.getResult();
        final Set<Map<String, ?>> set = createSet(rows.size(), keys);
        set.addAll(rows);
        return set;
    }

    private static class RestrictedKeyMapSet extends HashSet<Map<String, ?>> {
        private final String[] keys;

        public RestrictedKeyMapSet(final int size, final String[] keys) {
            super(size);
            this.keys = keys;
        }

        public boolean addAll(final Collection<? extends Map<String, ?>> maps) {
            boolean result=false;
            for (final Map<String, ?> map : maps) {
                result |= add(map);
            }
            return result;
        }

        public boolean removeAll(final Collection<?> objects) {
            boolean result=false;
            for (final Object value : objects) {
                result |= remove(value);
            }
            return result;
        }

        public boolean retainAll(final Collection<?> objects) {
            final Set<Map<String, ?>> other = new RestrictedKeyMapSet(objects.size(), keys);
            for (final Object object : objects) {
                if (object instanceof Map) {
                    other.add((Map<String, ?>) object);
                }
            }
            boolean result=true;
            for (final Iterator<Map<String,?>> it=iterator();it.hasNext();) {
                if (!other.contains(it.next())) {
                    it.remove();
                    result=false;
                }
            }
            return result;
        }

        public boolean add(final Map<String, ?> stringMap) {
            return super.add(makeRestrictedKey(stringMap));
        }

        public boolean remove(final Object o) {
            if (o instanceof Map)
                return super.remove(makeRestrictedKey(o));
            return super.remove(o);
        }

        private RestrictedKeys makeRestrictedKey(final Object o) {
            if (o instanceof RestrictedKeys) return (RestrictedKeys) o;
            return new RestrictedKeys((Map<String, Object>) o, keys);
        }

        public boolean contains(final Object o) {
            if (o instanceof Map)
                return super.contains(makeRestrictedKey(o));
            return super.contains(o);
        }
    }
}
