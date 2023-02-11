package com.google.gson;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.util.LinkedList;

public final class Gson {
    static final a a = new a();
    static final ay b = new ay();
    static final ak c = new ak(128, 8);
    static final w d = new q();
    static final k e = new aw(new o());
    private static final ExclusionStrategy f;
    private final ExclusionStrategy g;
    private final ExclusionStrategy h;
    private final k i;
    private final ai j;
    private final aq<JsonSerializer<?>> k;
    private final aq<JsonDeserializer<?>> l;
    private final w m;
    private final boolean n;
    private final boolean o;

    static {
        LinkedList linkedList = new LinkedList();
        linkedList.add(a);
        linkedList.add(b);
        linkedList.add(c);
        f = new g(linkedList);
    }

    public Gson() {
        this(f, f, e, new ai(f.c()), d, false, f.a(), f.b(), false);
    }

    Gson(ExclusionStrategy serializationStrategy, ExclusionStrategy deserializationStrategy, k fieldNamingPolicy, ai objectConstructor, w formatter, boolean serializeNulls, aq<JsonSerializer<?>> serializers, aq<JsonDeserializer<?>> deserializers, boolean generateNonExecutableGson) {
        this.g = serializationStrategy;
        this.h = deserializationStrategy;
        this.i = fieldNamingPolicy;
        this.j = objectConstructor;
        this.m = formatter;
        this.n = serializeNulls;
        this.k = serializers;
        this.l = deserializers;
        this.o = generateNonExecutableGson;
    }

    private an a(ExclusionStrategy exclusionStrategy) {
        return new an(exclusionStrategy, this.i);
    }

    private static void a(Appendable appendable) throws IOException {
        appendable.append("null");
    }

    public final <T> T fromJson(JsonElement json, Class<T> classOfT) throws JsonParseException {
        return classOfT.cast(fromJson(json, (Type) classOfT));
    }

    public final <T> T fromJson(JsonElement json, Type typeOfT) throws JsonParseException {
        if (json == null) {
            return null;
        }
        return (T) new r(a(this.h), this.l, this.j).deserialize(json, typeOfT);
    }

    public final <T> T fromJson(Reader json, Class<T> classOfT) throws JsonParseException {
        return classOfT.cast(fromJson(json, (Type) classOfT));
    }

    public final <T> T fromJson(Reader json, Type typeOfT) throws JsonParseException {
        return (T) fromJson(new JsonParser().parse(json), typeOfT);
    }

    public final <T> T fromJson(String json, Class<T> classOfT) throws JsonParseException {
        return (T) fromJson(json, (Type) classOfT);
    }

    public final <T> T fromJson(String json, Type typeOfT) throws JsonParseException {
        return (T) fromJson(new StringReader(json), typeOfT);
    }

    public final String toJson(JsonElement jsonElement) {
        StringWriter stringWriter = new StringWriter();
        toJson(jsonElement, (Appendable) stringWriter);
        return stringWriter.toString();
    }

    public final String toJson(Object src) {
        if (src == null) {
            return this.n ? "null" : "";
        }
        return toJson(src, src.getClass());
    }

    public final String toJson(Object src, Type typeOfSrc) {
        StringWriter stringWriter = new StringWriter();
        toJson(src, typeOfSrc, stringWriter);
        return stringWriter.toString();
    }

    public final void toJson(JsonElement jsonElement, Appendable writer) {
        try {
            if (this.o) {
                writer.append(")]}'\n");
            }
            if (jsonElement == null && this.n) {
                a(writer);
            }
            this.m.a(jsonElement, writer, this.n);
        } catch (IOException e2) {
            throw new RuntimeException(e2);
        }
    }

    public final void toJson(Object src, Appendable writer) {
        if (src != null) {
            try {
                toJson(src, src.getClass(), writer);
            } catch (IOException e2) {
                throw new RuntimeException(e2);
            }
        } else if (this.n) {
            a(writer);
        }
    }

    public final void toJson(Object src, Type typeOfSrc, Appendable writer) {
        toJson(toJsonTree(src, typeOfSrc), writer);
    }

    public final JsonElement toJsonTree(Object src) {
        return src == null ? JsonNull.b() : toJsonTree(src, src.getClass());
    }

    public final JsonElement toJsonTree(Object src, Type typeOfSrc) {
        return src == null ? JsonNull.b() : new ac(a(this.g), this.n, this.k).a(src, typeOfSrc);
    }

    public final String toString() {
        return "{" + "serializeNulls:" + this.n + ",serializers:" + this.k + ",deserializers:" + this.l + ",instanceCreators:" + this.j + "}";
    }
}
