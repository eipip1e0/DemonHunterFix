package com.google.gson;

import java.lang.reflect.Type;

final class x<T> extends s<T> {
    x(JsonElement jsonElement, Type type, an anVar, am amVar, aq<JsonDeserializer<?>> aqVar, JsonDeserializationContext jsonDeserializationContext) {
        super(jsonElement, type, anVar, amVar, aqVar, jsonDeserializationContext);
    }

    private String a(FieldAttributes fieldAttributes) {
        return this.a.a().a(fieldAttributes);
    }

    /* access modifiers changed from: protected */
    @Override // com.google.gson.s
    public final T a() {
        return (T) this.b.a(this.f);
    }

    @Override // com.google.gson.ObjectNavigator.Visitor
    public final void startVisitingObject(Object obj) {
    }

    @Override // com.google.gson.ObjectNavigator.Visitor
    public final void visitArray(Object array, Type type) {
        throw new JsonParseException("Expecting object but found array: " + array);
    }

    @Override // com.google.gson.ObjectNavigator.Visitor
    public final void visitArrayField(FieldAttributes f, Type typeOfF, Object obj) {
        try {
            if (!this.e.isJsonObject()) {
                throw new JsonParseException("Expecting object found: " + this.e);
            }
            JsonArray jsonArray = (JsonArray) this.e.getAsJsonObject().get(a(f));
            if (jsonArray != null) {
                f.a(obj, a(typeOfF, jsonArray));
            } else {
                f.a(obj, null);
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override // com.google.gson.ObjectNavigator.Visitor
    public final boolean visitFieldUsingCustomHandler(FieldAttributes f, Type declaredTypeOfField, Object parent) {
        try {
            String a = a(f);
            if (!this.e.isJsonObject()) {
                throw new JsonParseException("Expecting object found: " + this.e);
            }
            JsonElement jsonElement = this.e.getAsJsonObject().get(a);
            bb bbVar = new bb(declaredTypeOfField);
            if (jsonElement == null) {
                return true;
            }
            if (jsonElement.isJsonNull()) {
                if (!bbVar.d()) {
                    f.a(parent, null);
                }
                return true;
            }
            ap<JsonDeserializer<?>, ao> a2 = new ao(null, declaredTypeOfField, false).a(this.c);
            if (a2 == null) {
                return false;
            }
            Object a3 = a(jsonElement, a2);
            if (a3 != null || !bbVar.d()) {
                f.a(parent, a3);
            }
            return true;
        } catch (IllegalAccessException e) {
            throw new RuntimeException();
        }
    }

    @Override // com.google.gson.ObjectNavigator.Visitor
    public final void visitObjectField(FieldAttributes f, Type typeOfF, Object obj) {
        try {
            if (!this.e.isJsonObject()) {
                throw new JsonParseException("Expecting object found: " + this.e);
            }
            JsonElement jsonElement = this.e.getAsJsonObject().get(a(f));
            if (jsonElement != null) {
                f.a(obj, a(typeOfF, jsonElement));
            } else {
                f.a(obj, null);
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override // com.google.gson.ObjectNavigator.Visitor
    public final void visitPrimitive(Object obj) {
        if (!this.e.isJsonPrimitive()) {
            throw new JsonParseException("Type information is unavailable, and the target object is not a primitive: " + this.e);
        }
        this.d = this.e.getAsJsonPrimitive().a();
    }
}
