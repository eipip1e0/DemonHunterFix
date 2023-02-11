package com.google.gson;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

public abstract class JsonElement {
    private static final h a = new h(false);

    /* access modifiers changed from: package-private */
    public Object a() {
        throw new UnsupportedOperationException();
    }

    /* access modifiers changed from: protected */
    public abstract void a(Appendable appendable, h hVar) throws IOException;

    public BigDecimal getAsBigDecimal() {
        throw new UnsupportedOperationException();
    }

    public BigInteger getAsBigInteger() {
        throw new UnsupportedOperationException();
    }

    public boolean getAsBoolean() {
        throw new UnsupportedOperationException();
    }

    public byte getAsByte() {
        throw new UnsupportedOperationException();
    }

    public char getAsCharacter() {
        throw new UnsupportedOperationException();
    }

    public double getAsDouble() {
        throw new UnsupportedOperationException();
    }

    public float getAsFloat() {
        throw new UnsupportedOperationException();
    }

    public int getAsInt() {
        throw new UnsupportedOperationException();
    }

    public JsonArray getAsJsonArray() {
        if (isJsonArray()) {
            return (JsonArray) this;
        }
        throw new IllegalStateException("This is not a JSON Array.");
    }

    public JsonNull getAsJsonNull() {
        if (isJsonNull()) {
            return (JsonNull) this;
        }
        throw new IllegalStateException("This is not a JSON Null.");
    }

    public JsonObject getAsJsonObject() {
        if (isJsonObject()) {
            return (JsonObject) this;
        }
        throw new IllegalStateException("This is not a JSON Object.");
    }

    public JsonPrimitive getAsJsonPrimitive() {
        if (isJsonPrimitive()) {
            return (JsonPrimitive) this;
        }
        throw new IllegalStateException("This is not a JSON Primitive.");
    }

    public long getAsLong() {
        throw new UnsupportedOperationException();
    }

    public Number getAsNumber() {
        throw new UnsupportedOperationException();
    }

    public short getAsShort() {
        throw new UnsupportedOperationException();
    }

    public String getAsString() {
        throw new UnsupportedOperationException();
    }

    public boolean isJsonArray() {
        return this instanceof JsonArray;
    }

    public boolean isJsonNull() {
        return this instanceof JsonNull;
    }

    public boolean isJsonObject() {
        return this instanceof JsonObject;
    }

    public boolean isJsonPrimitive() {
        return this instanceof JsonPrimitive;
    }

    public String toString() {
        try {
            StringBuilder sb = new StringBuilder();
            a(sb, a);
            return sb.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
