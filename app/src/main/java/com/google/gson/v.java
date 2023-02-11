package com.google.gson;

import java.util.regex.Pattern;

final class v {
    private static final Pattern a = Pattern.compile("(^[a-zA-Z][a-zA-Z0-9\\ \\$_\\-]*$)|(^[\\$_][a-zA-Z][a-zA-Z0-9\\ \\$_\\-]*$)");

    v() {
    }

    public static String a(String str) {
        at.a(str);
        at.a(!"".equals(str.trim()));
        if (a.matcher(str).matches()) {
            return str;
        }
        throw new IllegalArgumentException(str + " is not a valid JSON field name.");
    }
}
