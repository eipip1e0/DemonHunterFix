package com.google.gson;

import com.google.gson.ObjectNavigator;
import java.lang.reflect.Type;

abstract class s<T> implements ObjectNavigator.Visitor {
    protected final an a;
    protected final am b;
    protected final aq<JsonDeserializer<?>> c;
    protected T d;
    protected final JsonElement e;
    protected final Type f;
    protected final JsonDeserializationContext g;
    protected boolean h = false;

    public s(JsonElement jsonElement, Type type, an anVar, am amVar, aq<JsonDeserializer<?>> aqVar, JsonDeserializationContext jsonDeserializationContext) {
        at.a(jsonElement);
        this.f = type;
        this.a = anVar;
        this.b = amVar;
        this.c = aqVar;
        this.e = jsonElement;
        this.g = jsonDeserializationContext;
    }

    private Object a(Type type, s<?> sVar) {
        this.a.a(new ao(null, type, false)).a(sVar);
        return sVar.getTarget();
    }

    /* access modifiers changed from: protected */
    public abstract T a();

    /* access modifiers changed from: protected */
    public final Object a(JsonElement jsonElement, ap<JsonDeserializer<?>, ao> apVar) {
        if (jsonElement == null || jsonElement.isJsonNull()) {
            return null;
        }
        return apVar.a.deserialize(jsonElement, apVar.b.a, this.g);
    }

    /* access modifiers changed from: package-private */
    public final Object a(Type type, JsonArray jsonArray) {
        return a(type, new p(jsonArray.getAsJsonArray(), type, this.a, this.b, this.c, this.g));
    }

    /* access modifiers changed from: package-private */
    public final Object a(Type type, JsonElement jsonElement) {
        return a(type, new x(jsonElement, type, this.a, this.b, this.c, this.g));
    }

    @Override // com.google.gson.ObjectNavigator.Visitor
    public void end(ao aoVar) {
    }

    @Override // com.google.gson.ObjectNavigator.Visitor
    public T getTarget() {
        if (!this.h) {
            this.d = a();
            this.h = true;
        }
        return this.d;
    }

    @Override // com.google.gson.ObjectNavigator.Visitor
    public void start(ao aoVar) {
    }

    @Override // com.google.gson.ObjectNavigator.Visitor
    public final boolean visitUsingCustomHandler(ao objTypePair) {
        ap<JsonDeserializer<?>, ao> a2 = objTypePair.a((aq) this.c);
        if (a2 == null) {
            return false;
        }
        this.d = (T) a(this.e, a2);
        this.h = true;
        return true;
    }
}
