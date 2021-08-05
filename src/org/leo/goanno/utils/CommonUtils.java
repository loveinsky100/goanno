package org.leo.goanno.utils;

public class CommonUtils {
    public static <T> T toValue(Class<? extends T> type, Object value, T defaultValue) {
        if (null == value) {
            return defaultValue;
        }

        if (value.getClass().isAssignableFrom(type)) {
            return (T)value;
        }

        if (type.isAssignableFrom(Boolean.class) && value instanceof String) {
            return value.equals("true") ? (T) Boolean.TRUE : (T) Boolean.FALSE;
        }

        return defaultValue;
    }
}
