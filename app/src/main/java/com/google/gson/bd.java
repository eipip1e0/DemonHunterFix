package com.google.gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

final class bd {
    private final ParameterizedType a;

    public bd(Type type) {
        if (!(type instanceof ParameterizedType)) {
            throw new IllegalArgumentException("Collection objects need to be parameterized unless you use a custom serializer. Use the com.google.gson.reflect.TypeToken to extract the ParameterizedType.");
        }
        at.a(Collection.class.isAssignableFrom(new bb(type).b()));
        this.a = (ParameterizedType) type;
    }

    public final Type a() {
        return this.a.getActualTypeArguments()[0];
    }
}
