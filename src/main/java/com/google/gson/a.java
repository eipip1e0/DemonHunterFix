package com.google.gson;

final class a implements ExclusionStrategy {
    a() {
    }

    private static boolean a(Class<?> cls) {
        return cls.isAnonymousClass() || cls.isLocalClass();
    }

    @Override // com.google.gson.ExclusionStrategy
    public final boolean shouldSkipClass(Class<?> clazz) {
        return a(clazz);
    }

    @Override // com.google.gson.ExclusionStrategy
    public final boolean shouldSkipField(FieldAttributes f) {
        return a(f.getDeclaredClass());
    }
}
