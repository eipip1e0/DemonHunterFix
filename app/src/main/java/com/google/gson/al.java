package com.google.gson;

/* access modifiers changed from: package-private */
public final class al implements ExclusionStrategy {
    al() {
    }

    @Override // com.google.gson.ExclusionStrategy
    public final boolean shouldSkipClass(Class<?> cls) {
        return false;
    }

    @Override // com.google.gson.ExclusionStrategy
    public final boolean shouldSkipField(FieldAttributes fieldAttributes) {
        return false;
    }
}
