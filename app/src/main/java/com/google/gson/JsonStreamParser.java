package com.google.gson;

import java.io.EOFException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Iterator;
import java.util.NoSuchElementException;

public final class JsonStreamParser implements Iterator<JsonElement> {
    private final y a;
    private final Object b;
    private JsonElement c;

    public JsonStreamParser(Reader reader) {
        this.a = new y(reader);
        this.b = new Object();
        this.c = null;
    }

    public JsonStreamParser(String json) {
        this(new StringReader(json));
    }

    public final boolean hasNext() {
        synchronized (this.b) {
            try {
                this.c = next();
            } catch (NoSuchElementException e) {
                this.c = null;
                return false;
            }
        }
        return true;
    }

    @Override // java.util.Iterator
    public final JsonElement next() throws JsonParseException {
        synchronized (this.b) {
            if (this.c != null) {
                JsonElement jsonElement = this.c;
                this.c = null;
                return jsonElement;
            }
            try {
                return this.a.a();
            } catch (ba e) {
                throw new JsonParseException("Failed parsing JSON source to Json", e);
            } catch (as e2) {
                throw new JsonParseException("Failed parsing JSON source to Json", e2);
            } catch (StackOverflowError e3) {
                throw new JsonParseException("Failed parsing JSON source to Json", e3);
            } catch (OutOfMemoryError e4) {
                throw new JsonParseException("Failed parsing JSON source to Json", e4);
            } catch (JsonParseException e5) {
                if (e5.getCause() instanceof EOFException) {
                    throw new NoSuchElementException();
                }
                throw e5;
            }
        }
    }

    public final void remove() {
        throw new UnsupportedOperationException();
    }
}
