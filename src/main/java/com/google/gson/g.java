package com.google.gson;

import java.util.Collection;

final class g implements ExclusionStrategy {
    private final Collection<ExclusionStrategy> a;

    public g(Collection<ExclusionStrategy> collection) {
        at.a(collection);
        this.a = collection;
    }

    @Override // com.google.gson.ExclusionStrategy
    public final boolean shouldSkipClass(Class<?> clazz) {
        for (ExclusionStrategy exclusionStrategy : this.a) {
            if (exclusionStrategy.shouldSkipClass(clazz)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.google.gson.ExclusionStrategy
    public final boolean shouldSkipField(FieldAttributes f) {
        for (ExclusionStrategy exclusionStrategy : this.a) {
            if (exclusionStrategy.shouldSkipField(f)) {
                return true;
            }
        }
        return false;
    }
}
