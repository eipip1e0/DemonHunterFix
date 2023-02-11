package com.google.gson;

import java.lang.reflect.Array;
import java.lang.reflect.Type;

final class p<T> extends s<T> {
    p(JsonArray jsonArray, Type type, an anVar, am amVar, aq<JsonDeserializer<?>> aqVar, JsonDeserializationContext jsonDeserializationContext) {
        super(jsonArray, type, anVar, amVar, aqVar, jsonDeserializationContext);
    }

    /* access modifiers changed from: protected */
    @Override // com.google.gson.s
    public final T a() {
        bb bbVar = new bb(this.f);
        if (!this.e.isJsonArray()) {
            throw new JsonParseException("Expecting array found: " + this.e);
        }
        JsonArray asJsonArray = this.e.getAsJsonArray();
        if (!bbVar.c()) {
            return (T) this.b.a(bbVar.b());
        }
        return (T) this.b.a(be.a(this.f).e(), asJsonArray.size());
    }

    @Override // com.google.gson.ObjectNavigator.Visitor
    public final void startVisitingObject(Object node) {
        throw new JsonParseException("Expecting array but found object: " + node);
    }

    @Override // com.google.gson.ObjectNavigator.Visitor
    public final void visitArray(Object array, Type arrayType) {
        Object obj;
        if (!this.e.isJsonArray()) {
            throw new JsonParseException("Expecting array found: " + this.e);
        }
        JsonArray asJsonArray = this.e.getAsJsonArray();
        bc a = be.a(arrayType);
        for (int i = 0; i < asJsonArray.size(); i++) {
            JsonElement jsonElement = asJsonArray.get(i);
            if (jsonElement == null || jsonElement.isJsonNull()) {
                obj = null;
            } else if (jsonElement instanceof JsonObject) {
                obj = a(a.f(), jsonElement);
            } else if (jsonElement instanceof JsonArray) {
                obj = a(a.e(), jsonElement.getAsJsonArray());
            } else if (jsonElement instanceof JsonPrimitive) {
                obj = a(a.f(), jsonElement.getAsJsonPrimitive());
            } else {
                throw new IllegalStateException();
            }
            Array.set(array, i, obj);
        }
    }

    @Override // com.google.gson.ObjectNavigator.Visitor
    public final void visitArrayField(FieldAttributes f, Type type, Object obj) {
        throw new JsonParseException("Expecting array but found array field " + f.getName() + ": " + obj);
    }

    @Override // com.google.gson.ObjectNavigator.Visitor
    public final boolean visitFieldUsingCustomHandler(FieldAttributes f, Type type, Object parent) {
        throw new JsonParseException("Expecting array but found field " + f.getName() + ": " + parent);
    }

    @Override // com.google.gson.ObjectNavigator.Visitor
    public final void visitObjectField(FieldAttributes f, Type type, Object obj) {
        throw new JsonParseException("Expecting array but found object field " + f.getName() + ": " + obj);
    }

    @Override // com.google.gson.ObjectNavigator.Visitor
    public final void visitPrimitive(Object obj) {
        throw new JsonParseException("Type information is unavailable, and the target is not a primitive: " + this.e);
    }
}
