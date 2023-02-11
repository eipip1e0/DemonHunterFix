package com.google.gson;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* access modifiers changed from: package-private */
public final class au {
    public static final Map<Class<?>, Class<?>> a;
    public static final Map<Class<?>, Class<?>> b;

    static {
        HashMap hashMap = new HashMap(16);
        HashMap hashMap2 = new HashMap(16);
        a(hashMap, hashMap2, Boolean.TYPE, Boolean.class);
        a(hashMap, hashMap2, Byte.TYPE, Byte.class);
        a(hashMap, hashMap2, Character.TYPE, Character.class);
        a(hashMap, hashMap2, Double.TYPE, Double.class);
        a(hashMap, hashMap2, Float.TYPE, Float.class);
        a(hashMap, hashMap2, Integer.TYPE, Integer.class);
        a(hashMap, hashMap2, Long.TYPE, Long.class);
        a(hashMap, hashMap2, Short.TYPE, Short.class);
        a(hashMap, hashMap2, Void.TYPE, Void.class);
        a = Collections.unmodifiableMap(hashMap);
        b = Collections.unmodifiableMap(hashMap2);
    }

    private au() {
    }

    private static void a(Map<Class<?>, Class<?>> map, Map<Class<?>, Class<?>> map2, Class<?> cls, Class<?> cls2) {
        map.put(cls, cls2);
        map2.put(cls2, cls);
    }

    public static boolean a(Class<?> cls) {
        Map<Class<?>, Class<?>> map = b;
        at.a(cls);
        return map.containsKey(cls);
    }

    public static <T> Class<T> b(Class<T> cls) {
        at.a(cls);
        Class<T> cls2 = (Class<T>) a.get(cls);
        return cls2 == null ? cls : cls2;
    }

    public static <T> Class<T> c(Class<T> cls) {
        at.a(cls);
        Class<T> cls2 = (Class<T>) b.get(cls);
        return cls2 == null ? cls : cls2;
    }
}
