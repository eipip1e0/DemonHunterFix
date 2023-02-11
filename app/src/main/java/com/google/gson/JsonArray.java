package com.google.gson;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class JsonArray extends JsonElement implements Iterable<JsonElement> {
    private final List<JsonElement> a = new ArrayList();

    /* access modifiers changed from: package-private */
    @Override // com.google.gson.JsonElement
    public final Object a() {
        if (this.a.size() == 1) {
            return this.a.get(0).a();
        }
        throw new IllegalStateException();
    }

    /* access modifiers changed from: protected */
    @Override // com.google.gson.JsonElement
    public final void a(Appendable appendable, h hVar) throws IOException {
        appendable.append('[');
        boolean z = true;
        for (JsonElement jsonElement : this.a) {
            if (z) {
                z = false;
            } else {
                appendable.append(',');
            }
            jsonElement.a(appendable, hVar);
        }
        appendable.append(']');
    }

    public final void add(JsonElement element) {
        if (element == null) {
            element = JsonNull.b();
        }
        this.a.add(element);
    }

    public final void addAll(JsonArray array) {
        this.a.addAll(array.a);
    }

    public final JsonElement get(int i) {
        return this.a.get(i);
    }

    @Override // com.google.gson.JsonElement
    public final BigDecimal getAsBigDecimal() {
        if (this.a.size() == 1) {
            return this.a.get(0).getAsBigDecimal();
        }
        throw new IllegalStateException();
    }

    @Override // com.google.gson.JsonElement
    public final BigInteger getAsBigInteger() {
        if (this.a.size() == 1) {
            return this.a.get(0).getAsBigInteger();
        }
        throw new IllegalStateException();
    }

    @Override // com.google.gson.JsonElement
    public final boolean getAsBoolean() {
        if (this.a.size() == 1) {
            return this.a.get(0).getAsBoolean();
        }
        throw new IllegalStateException();
    }

    @Override // com.google.gson.JsonElement
    public final byte getAsByte() {
        if (this.a.size() == 1) {
            return this.a.get(0).getAsByte();
        }
        throw new IllegalStateException();
    }

    @Override // com.google.gson.JsonElement
    public final char getAsCharacter() {
        if (this.a.size() == 1) {
            return this.a.get(0).getAsCharacter();
        }
        throw new IllegalStateException();
    }

    @Override // com.google.gson.JsonElement
    public final double getAsDouble() {
        if (this.a.size() == 1) {
            return this.a.get(0).getAsDouble();
        }
        throw new IllegalStateException();
    }

    @Override // com.google.gson.JsonElement
    public final float getAsFloat() {
        if (this.a.size() == 1) {
            return this.a.get(0).getAsFloat();
        }
        throw new IllegalStateException();
    }

    @Override // com.google.gson.JsonElement
    public final int getAsInt() {
        if (this.a.size() == 1) {
            return this.a.get(0).getAsInt();
        }
        throw new IllegalStateException();
    }

    @Override // com.google.gson.JsonElement
    public final long getAsLong() {
        if (this.a.size() == 1) {
            return this.a.get(0).getAsLong();
        }
        throw new IllegalStateException();
    }

    @Override // com.google.gson.JsonElement
    public final Number getAsNumber() {
        if (this.a.size() == 1) {
            return this.a.get(0).getAsNumber();
        }
        throw new IllegalStateException();
    }

    @Override // com.google.gson.JsonElement
    public final short getAsShort() {
        if (this.a.size() == 1) {
            return this.a.get(0).getAsShort();
        }
        throw new IllegalStateException();
    }

    @Override // com.google.gson.JsonElement
    public final String getAsString() {
        if (this.a.size() == 1) {
            return this.a.get(0).getAsString();
        }
        throw new IllegalStateException();
    }

    @Override // java.lang.Iterable
    public final Iterator<JsonElement> iterator() {
        return this.a.iterator();
    }

    public final int size() {
        return this.a.size();
    }
}
