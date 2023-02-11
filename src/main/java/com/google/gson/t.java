package com.google.gson;

import java.lang.reflect.Type;

/* access modifiers changed from: package-private */
public final class t<T> implements JsonDeserializer<T> {
    private final JsonDeserializer<T> a;

    t(JsonDeserializer<T> jsonDeserializer) {
        at.a(jsonDeserializer);
        this.a = jsonDeserializer;
    }

    @Override // com.google.gson.JsonDeserializer
    public final T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            return this.a.deserialize(json, typeOfT, context);
        } catch (JsonParseException e) {
            throw e;
        } catch (Exception e2) {
            throw new JsonParseException("The JsonDeserializer " + this.a + " failed to deserialized json object " + json + " given the type " + typeOfT, e2);
        }
    }

    public final String toString() {
        return this.a.toString();
    }
}
