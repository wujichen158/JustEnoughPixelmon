package com.duckfox.jep.utils;

import java.lang.reflect.Field;

public final class Reflections {
    public static  <T> T get(Object obj, String fieldName, Class<T> type){
        Field field;
        try {
            field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return type.cast(field.get(obj));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
