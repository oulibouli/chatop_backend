package com.chatop.portal.utils;

public class StringUtils {
    public static boolean isNullOrEmpty (String str) {
        return str == null || str.trim().isEmpty();
    }
}
