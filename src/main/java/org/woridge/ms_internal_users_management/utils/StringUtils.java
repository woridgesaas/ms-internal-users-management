package org.woridge.ms_internal_users_management.utils;

public final class StringUtils {
    private StringUtils() {}

    public static String trimToNull(String s) {
        if (s == null) return null;
        String t = s.trim();
        return t.isEmpty() ? null : t;
    }
}
