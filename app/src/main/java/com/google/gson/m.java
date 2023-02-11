package com.google.gson;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;

/* access modifiers changed from: package-private */
public final class m implements GenericArrayType {
    private final Type a;

    public m(Type type) {
        this.a = type;
    }

    public final boolean equals(Object o) {
        if (!(o instanceof GenericArrayType)) {
            return false;
        }
        Type genericComponentType = ((GenericArrayType) o).getGenericComponentType();
        if (this.a == null) {
            return genericComponentType == null;
        }
        return this.a.equals(genericComponentType);
    }

    public final Type getGenericComponentType() {
        return this.a;
    }

    public final int hashCode() {
        if (this.a == null) {
            return 0;
        }
        return this.a.hashCode();
    }
}
