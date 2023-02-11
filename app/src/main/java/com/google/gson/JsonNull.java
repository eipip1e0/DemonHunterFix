package com.google.gson;

import java.io.IOException;

public final class JsonNull extends JsonElement {
    private static final JsonNull a = new JsonNull();

    static JsonNull b() {
        return a;
    }

    /* access modifiers changed from: protected */
    @Override // com.google.gson.JsonElement
    public final void a(Appendable appendable, h hVar) throws IOException {
        appendable.append("null");
    }

    public final boolean equals(Object other) {
        return other instanceof JsonNull;
    }

    public final int hashCode() {
        return JsonNull.class.hashCode();
    }
}
