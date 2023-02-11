package com.google.gson;

import java.io.EOFException;
import java.io.Reader;
import java.io.StringReader;

public final class JsonParser {
    public final JsonElement parse(Reader json) throws JsonParseException {
        try {
            return new y(json).a();
        } catch (ba e) {
            throw new JsonParseException("Failed parsing JSON source: " + json + " to Json", e);
        } catch (as e2) {
            throw new JsonParseException("Failed parsing JSON source: " + json + " to Json", e2);
        } catch (StackOverflowError e3) {
            throw new JsonParseException("Failed parsing JSON source: " + json + " to Json", e3);
        } catch (OutOfMemoryError e4) {
            throw new JsonParseException("Failed parsing JSON source: " + json + " to Json", e4);
        } catch (JsonParseException e5) {
            if (e5.getCause() instanceof EOFException) {
                return JsonNull.b();
            }
            throw e5;
        }
    }

    public final JsonElement parse(String json) throws JsonParseException {
        return parse(new StringReader(json));
    }
}
