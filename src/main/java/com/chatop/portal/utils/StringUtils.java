package com.chatop.portal.utils;

public class StringUtils {
    public static boolean isNullOrEmpty (String str) {
        return str == null || str.trim().isEmpty();
    }

    public static boolean isNotValidAuthorizationHeader(String authHeader) {
        return authHeader == null || authHeader.isBlank() || !authHeader.startsWith("Bearer ");
    }
}
