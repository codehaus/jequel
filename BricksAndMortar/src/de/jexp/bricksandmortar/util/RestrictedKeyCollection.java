package de.jexp.bricksandmortar.util;

import de.jexp.bricksandmortar.results.ListStepResult;

import java.util.*;

/**
 * @author mhu@salt-solutions.de
* @copyright (c) Salt Solutions GmbH 2006
* @since 07.12.2007 10:02:50
*/
public class RestrictedKeyCollection implements Collection<Map<String, ?>> {
    private final String[] keys;
    private final boolean distinct;
    private final Collection<Map<String, ?>> collection;
    public RestrictedKeyCollection(final String[] keys, final boolean distinct, final Collection<Map<String, ?>> collection) {
        this.distinct = distinct;
        this.collection = collection;
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
            if (result) {
                while(remove(value));
            }
        }
        return result;
    }

    public boolean retainAll(final Collection<?> objects) {
        final Collection<Map<String, ?>> other = new RestrictedKeyCollection(keys, distinct, createCollection(objects.size(), distinct));
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
        return collection.add(makeRestrictedKey(stringMap));
    }

    public boolean remove(final Object o) {
        if (o instanceof Map)
            return collection.remove(makeRestrictedKey((Map<String, ?>) o));
        return collection.remove(o);
    }

    private Map<String, ?> makeRestrictedKey(final Map<String, ?> o) {
        if (!hasKeys(keys)) return o;
        if (o instanceof RestrictedKeys) return o;
        return new RestrictedKeys((Map<String, Object>) o, keys);

    }

    public boolean contains(final Object o) {
        if (o instanceof Map)
            return collection.contains(makeRestrictedKey((Map<String, ?>) o));
        return collection.contains(o);
    }

    public int size() {
        return collection.size() ;
    }

    public boolean isEmpty() {
        return collection.isEmpty();
    }

    public Iterator<Map<String, ?>> iterator() {
        return collection.iterator();
    }

    public Object[] toArray() {
        return collection.toArray();
    }

    public <T> T[] toArray(final T[] a) {
        return collection.toArray(a);
    }

    public boolean containsAll(final Collection<?> other) {
        for (final Object element : other) {
            if (!contains(element))
                return false;
        }
        return true;
    }

    public void clear() {
        collection.clear();
    }

    public static Collection<Map<String, ?>> resolve(final Collection<Map<String, ?>> result, final String[] keys) {
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

    public static Collection<Map<String, ?>> createKeyBasedCollection(final int size, final boolean distinct, final String[] keys) {
        if (!hasKeys(keys))
            return createCollection(size, distinct);
        return new RestrictedKeyCollection(keys, distinct, createCollection(size, distinct));
    }

    public static AbstractCollection<Map<String, ?>> createCollection(final int size, final boolean distinct) {
        return distinct ? new HashSet<Map<String, ?>>(size) : new ArrayList<Map<String, ?>>(size);
    }

    public static Collection<Map<String, ?>> createSet(final ListStepResult list, boolean distinct, final String[] keys) {
        final Collection<Map<String, ?>> rows = list.getResult();
        final Collection<Map<String, ?>> keyBaseCollection = createKeyBasedCollection(rows.size(), distinct, keys);
        keyBaseCollection.addAll(rows);
        return keyBaseCollection;
    }

    public String toString() {
        return collection.toString();
    }
}
