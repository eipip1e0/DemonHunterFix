package com.google.gson;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public final class JsonObject extends JsonElement {
    private final Map<String, JsonElement> a = new LinkedHashMap();

    private static JsonElement a(Object obj) {
        return obj == null ? JsonNull.b() : new JsonPrimitive(obj);
    }

    /* access modifiers changed from: protected */
    @Override // com.google.gson.JsonElement
    public final void a(Appendable appendable, h hVar) throws IOException {
        appendable.append('{');
        boolean z = true;
        for (Map.Entry<String, JsonElement> entry : this.a.entrySet()) {
            if (z) {
                z = false;
            } else {
                appendable.append(',');
            }
            appendable.append('\"');
            appendable.append(entry.getKey());
            appendable.append("\":");
            entry.getValue().a(appendable, hVar);
            z = z;
        }
        appendable.append('}');
    }

    public final void add(String property, JsonElement value) {
        at.a(property != null && !"".equals(property.trim()));
        if (value == null) {
            value = JsonNull.b();
        }
        this.a.put(property, value);
    }

    public final void addProperty(String property, Boolean value) {
        add(property, a(value));
    }

    public final void addProperty(String property, Character value) {
        add(property, a(value));
    }

    public final void addProperty(String property, Number value) {
        add(property, a(value));
    }

    public final void addProperty(String property, String value) {
        add(property, a(value));
    }

    public final Set<Map.Entry<String, JsonElement>> entrySet() {
        return this.a.entrySet();
    }

    public final JsonElement get(String memberName) {
        if (!this.a.containsKey(memberName)) {
            return null;
        }
        JsonElement jsonElement = this.a.get(memberName);
        return jsonElement == null ? JsonNull.b() : jsonElement;
    }

    public final JsonArray getAsJsonArray(String memberName) {
        return (JsonArray) this.a.get(memberName);
    }

    public final JsonObject getAsJsonObject(String memberName) {
        return (JsonObject) this.a.get(memberName);
    }

    public final JsonPrimitive getAsJsonPrimitive(String memberName) {
        return (JsonPrimitive) this.a.get(memberName);
    }

    public final boolean has(String memberName) {
        return this.a.containsKey(memberName);
    }

    public final JsonElement remove(String property) {
        return this.a.remove(property);
    }
}
