package com.google.gson;

import java.lang.reflect.Type;

/* access modifiers changed from: package-private */
public final class ac implements JsonSerializationContext {
    private final an a;
    private final aq<JsonSerializer<?>> b;
    private final boolean c;
    private final aj d = new aj();

    ac(an anVar, boolean z, aq<JsonSerializer<?>> aqVar) {
        this.a = anVar;
        this.c = z;
        this.b = aqVar;
    }

    public final JsonElement a(Object obj, Type type) {
        ObjectNavigator a2 = this.a.a(new ao(obj, type, true));
        ad adVar = new ad(this.a, this.c, this.b, this, this.d);
        a2.a(adVar);
        return adVar.a();
    }

    @Override // com.google.gson.JsonSerializationContext
    public final JsonElement serialize(Object src) {
        return src == null ? JsonNull.b() : a(src, src.getClass());
    }

    @Override // com.google.gson.JsonSerializationContext
    public final JsonElement serialize(Object src, Type typeOfSrc) {
        return a(src, typeOfSrc);
    }
}
