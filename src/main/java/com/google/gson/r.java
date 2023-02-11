package com.google.gson;

import java.lang.reflect.Type;

/* access modifiers changed from: package-private */
public final class r implements JsonDeserializationContext {
    private final an a;
    private final aq<JsonDeserializer<?>> b;
    private final ai c;

    r(an anVar, aq<JsonDeserializer<?>> aqVar, ai aiVar) {
        this.a = anVar;
        this.b = aqVar;
        this.c = aiVar;
    }

    /* access modifiers changed from: package-private */
    public final am a() {
        return this.c;
    }

    @Override // com.google.gson.JsonDeserializationContext
    public final <T> T deserialize(JsonElement json, Type typeOfT) throws JsonParseException {
        if (json == null || json.isJsonNull()) {
            return null;
        }
        if (json.isJsonArray()) {
            p pVar = new p(json.getAsJsonArray(), typeOfT, this.a, this.c, this.b, this);
            this.a.a(new ao(null, typeOfT, true)).a(pVar);
            return (T) pVar.getTarget();
        } else if (json.isJsonObject()) {
            x xVar = new x(json.getAsJsonObject(), typeOfT, this.a, this.c, this.b, this);
            this.a.a(new ao(null, typeOfT, true)).a(xVar);
            return (T) xVar.getTarget();
        } else if (json.isJsonPrimitive()) {
            JsonPrimitive asJsonPrimitive = json.getAsJsonPrimitive();
            x xVar2 = new x(asJsonPrimitive, typeOfT, this.a, this.c, this.b, this);
            this.a.a(new ao(asJsonPrimitive.a(), typeOfT, true)).a(xVar2);
            return (T) xVar2.getTarget();
        } else {
            throw new JsonParseException("Failed parsing JSON source: " + json + " to Json");
        }
    }
}
