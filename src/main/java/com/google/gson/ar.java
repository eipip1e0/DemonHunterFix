package com.google.gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

/* access modifiers changed from: package-private */
public final class ar implements ParameterizedType {
    private final Type a;
    private final Type[] b;
    private final Type c;

    public ar(Type type, Type[] typeArr, Type type2) {
        this.a = type;
        this.b = typeArr;
        this.c = type2;
    }

    public final boolean equals(Object o) {
        if (!(o instanceof ParameterizedType)) {
            return false;
        }
        ParameterizedType parameterizedType = (ParameterizedType) o;
        if (this == parameterizedType) {
            return true;
        }
        Type ownerType = parameterizedType.getOwnerType();
        Type rawType = parameterizedType.getRawType();
        if (this.c != null ? this.c.equals(ownerType) : ownerType == null) {
            if (this.a != null ? this.a.equals(rawType) : rawType == null) {
                if (Arrays.equals(this.b, parameterizedType.getActualTypeArguments())) {
                    return true;
                }
            }
        }
        return false;
    }

    public final Type[] getActualTypeArguments() {
        return this.b;
    }

    public final Type getOwnerType() {
        return this.c;
    }

    public final Type getRawType() {
        return this.a;
    }

    public final int hashCode() {
        return (Arrays.hashCode(this.b) ^ (this.c == null ? 0 : this.c.hashCode())) ^ (this.a == null ? 0 : this.a.hashCode());
    }
}
