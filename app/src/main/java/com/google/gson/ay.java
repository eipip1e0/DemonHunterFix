package com.google.gson;

final class ay implements ExclusionStrategy {
    private final boolean a = true;

    ay() {
    }

    @Override // com.google.gson.ExclusionStrategy
    public final boolean shouldSkipClass(Class<?> cls) {
        return false;
    }

    @Override // com.google.gson.ExclusionStrategy
    public final boolean shouldSkipField(FieldAttributes f) {
        return this.a && f.a();
    }
}
