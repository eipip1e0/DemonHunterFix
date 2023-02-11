package com.google.gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Properties;

final class bf {
    private final Type a;
    private final Type b;

    public bf(Type type) {
        if ((type instanceof Class) && Properties.class.isAssignableFrom((Class) type)) {
            this.a = String.class;
            this.b = String.class;
        } else if (type instanceof ParameterizedType) {
            at.a(Map.class.isAssignableFrom(new bb(type).b()));
            ParameterizedType parameterizedType = (ParameterizedType) type;
            this.a = parameterizedType.getActualTypeArguments()[0];
            this.b = parameterizedType.getActualTypeArguments()[1];
        } else {
            throw new IllegalArgumentException("Map objects need to be parameterized unless you use a custom serializer. Use the com.google.gson.reflect.TypeToken to extract the ParameterizedType.");
        }
    }

    public final Type a() {
        return this.a;
    }

    public final Type b() {
        return this.b;
    }
}
