package com.google.gson;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;

/* access modifiers changed from: package-private */
public final class bg {
    private bg() {
    }

    static Class<?> a(Class<?> cls) {
        return Array.newInstance(cls, 0).getClass();
    }

    static boolean a(Type type) {
        return type instanceof Class ? ((Class) type).isArray() : type instanceof GenericArrayType;
    }

    static Class<?> b(Type type) {
        Type type2;
        while (!(type2 instanceof Class)) {
            if (type2 instanceof ParameterizedType) {
                type2 = ((ParameterizedType) type2).getRawType();
            } else if (type2 instanceof GenericArrayType) {
                return a(b(((GenericArrayType) type2).getGenericComponentType()));
            } else {
                if (type2 instanceof WildcardType) {
                    type2 = ((WildcardType) type2).getUpperBounds()[0];
                } else {
                    throw new IllegalArgumentException("Type '" + type2 + "' is not a Class, " + "ParameterizedType, or GenericArrayType. Can't extract class.");
                }
            }
        }
        return (Class) type2;
    }
}
