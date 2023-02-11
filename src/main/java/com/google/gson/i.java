package com.google.gson;

import com.google.gson.annotations.Expose;

final class i implements ExclusionStrategy {
    i() {
    }

    @Override // com.google.gson.ExclusionStrategy
    public final boolean shouldSkipClass(Class<?> cls) {
        return false;
    }

    @Override // com.google.gson.ExclusionStrategy
    public final boolean shouldSkipField(FieldAttributes f) {
        Expose expose = (Expose) f.getAnnotation(Expose.class);
        if (expose == null) {
            return true;
        }
        return !expose.deserialize();
    }
}