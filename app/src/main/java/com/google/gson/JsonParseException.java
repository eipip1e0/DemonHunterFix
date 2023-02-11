package com.google.gson;

public final class JsonParseException extends RuntimeException {
    public JsonParseException(String msg) {
        super(msg);
    }

    public JsonParseException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public JsonParseException(Throwable cause) {
        super(cause);
    }
}
