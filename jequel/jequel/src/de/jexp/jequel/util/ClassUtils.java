package de.jexp.jequel.util;

/**
 * @author mh14 @ jexp.de
 * @since 05.11.2007 02:15:43 (c) 2007 jexp.de
 */
public class ClassUtils {
    public static <T> T newInstance(final String typeName) {
        try {
            final Class<T> type = (Class<T>) Class.forName(typeName);
            return type.newInstance();
        } catch (InstantiationException e) {
            throw new IllegalArgumentException("Could not instantiate " + typeName, e);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException("Could not instantiate " + typeName, e);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Could not instantiate " + typeName, e);
        }
    }
}
