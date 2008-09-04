package de.jexp.bricksandmortar.database;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;
import java.lang.reflect.Field;

/**
 * @author mhu@salt-solutions.de
 * @copyright (c) Salt Solutions GmbH 2006
 * @since 03.12.2007 10:42:57
 */
public class SqlTypeUtils {
    static Map<String,Integer> typeCache =new HashMap<String, Integer>();

    public static int getType(final String type) {
        return getTypeCache().get(type);
    }

    private synchronized static Map<String, Integer> getTypeCache() {
        if (!typeCache.isEmpty()) return typeCache;
        for (Field field : Types.class.getDeclaredFields()) {
            try {
                typeCache.put(field.getName(), (Integer) field.get(null));
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Error accessing Field "+field, e);
            }
        }
        return typeCache;
    }

    public static String getName(final int type) {
        for (Map.Entry<String, Integer> nameTypeEntry : getTypeCache().entrySet()) {
            if (nameTypeEntry.getValue().equals(type)) return nameTypeEntry.getKey();
        }
        throw new IllegalArgumentException(String.format("Type %d unkown",type));
    }
}
