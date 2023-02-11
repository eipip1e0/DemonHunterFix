package com.google.gson;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;

/* access modifiers changed from: package-private */
public final class bc extends bb {
    private final Class<?> c;
    private final Type d;

    bc(Type type) {
        super(type);
        Class<?> cls = this.b;
        while (cls.isArray()) {
            cls = cls.getComponentType();
        }
        this.c = cls;
        Type type2 = this.a;
        this.d = type2 instanceof GenericArrayType ? ((GenericArrayType) type2).getGenericComponentType() : this.b.getComponentType();
    }

    public final Type e() {
        return this.d;
    }

    public final Class<?> f() {
        return this.c;
    }
}
