package com.srgiovine.seshopping.util;

import java.util.Collection;

public final class StringUtils {

    private StringUtils() {
    }

    public static String[] toStringArray(Collection... collections) {
        String[] strings = new String[collectionsSize(collections)];
        int i = 0;
        for (Collection collection : collections) {
            for (Object object : collection) {
                strings[i++] = String.valueOf(object);
            }
        }
        return strings;
    }

    private static int collectionsSize(Collection... collections) {
        int size = 0;
        for (Collection collection : collections) {
            size += collection.size();
        }
        return size;
    }

    public static String createCSVPlaceholders(String placeholder, int count) {
        StringBuilder sb = new StringBuilder(count * 2 - 1);
        sb.append(placeholder);
        for (int i = 1; i < count; i++) {
            sb.append(",");
            sb.append(placeholder);
        }
        return sb.toString();
    }

}
