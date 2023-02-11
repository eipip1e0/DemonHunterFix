package com.google.gson;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

final class ae {
    private final u a;
    private final boolean b;

    ae(u uVar, boolean z) {
        this.a = uVar;
        this.b = z;
    }

    public final void a(JsonElement jsonElement) throws IOException {
        boolean z;
        if (jsonElement.isJsonNull()) {
            this.a.a();
        } else if (jsonElement.isJsonArray()) {
            JsonArray asJsonArray = jsonElement.getAsJsonArray();
            this.a.b();
            Iterator<JsonElement> it = asJsonArray.iterator();
            boolean z2 = true;
            while (it.hasNext()) {
                JsonElement next = it.next();
                if (next.isJsonNull()) {
                    this.a.c(z2);
                    a(next);
                } else if (next.isJsonArray()) {
                    JsonArray asJsonArray2 = next.getAsJsonArray();
                    this.a.a(z2);
                    a(asJsonArray2);
                } else if (next.isJsonObject()) {
                    JsonObject asJsonObject = next.getAsJsonObject();
                    this.a.b(z2);
                    a(asJsonObject);
                } else {
                    this.a.a(next.getAsJsonPrimitive(), z2);
                }
                z2 = z2 ? false : z2;
            }
            this.a.c();
        } else if (jsonElement.isJsonObject()) {
            JsonObject asJsonObject2 = jsonElement.getAsJsonObject();
            this.a.d();
            boolean z3 = true;
            for (Map.Entry<String, JsonElement> entry : asJsonObject2.entrySet()) {
                String key = entry.getKey();
                JsonElement value = entry.getValue();
                if (value.isJsonNull()) {
                    if (this.b) {
                        this.a.c(key, z3);
                        a(value.getAsJsonNull());
                    } else {
                        z = false;
                        z3 = (z || !z3) ? z3 : false;
                    }
                } else if (value.isJsonArray()) {
                    JsonArray asJsonArray3 = value.getAsJsonArray();
                    this.a.a(key, z3);
                    a(asJsonArray3);
                } else if (value.isJsonObject()) {
                    JsonObject asJsonObject3 = value.getAsJsonObject();
                    this.a.b(key, z3);
                    a(asJsonObject3);
                } else {
                    this.a.a(key, value.getAsJsonPrimitive(), z3);
                }
                z = true;
                z3 = (z || !z3) ? z3 : false;
            }
            this.a.e();
        } else {
            this.a.a(jsonElement.getAsJsonPrimitive());
        }
    }
}
