package com.google.gson;

final class n implements ExclusionStrategy {
    n() {
    }

    private static boolean a(Class<?> cls) {
        if (cls.isMemberClass()) {
            if (!((cls.getModifiers() & 8) != 0)) {
                return true;
            }
        }
        return false;
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
