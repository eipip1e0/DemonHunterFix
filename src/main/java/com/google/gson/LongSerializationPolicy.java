package com.google.gson;

public enum LongSerializationPolicy {
    DEFAULT(new a()),
    STRING(new c());
    
    private final b a;

    private static class a implements b {
        /* synthetic */ a() {
            this((byte) 0);
        }

        private a(byte b) {
        }

        @Override // com.google.gson.LongSerializationPolicy.b
        public final JsonElement a(Long l) {
            return new JsonPrimitive((Number) l);
        }
    }

    /* access modifiers changed from: private */
    public interface b {
        JsonElement a(Long l);
    }

    private static class c implements b {
        /* synthetic */ c() {
            this((byte) 0);
        }

        private c(byte b) {
        }

        @Override // com.google.gson.LongSerializationPolicy.b
        public final JsonElement a(Long l) {
            return new JsonPrimitive(String.valueOf(l));
        }
    }

    private LongSerializationPolicy(b strategy) {
        this.a = strategy;
    }

    public final JsonElement serialize(Long value) {
        return this.a.a(value);
    }
}
