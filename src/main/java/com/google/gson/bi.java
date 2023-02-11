package com.google.gson;

import com.google.gson.annotations.Since;
import com.google.gson.annotations.Until;

final class bi implements ExclusionStrategy {
    private final double a;

    public bi(double d) {
        at.a(d >= 0.0d);
        this.a = d;
    }

    private boolean a(Since since, Until until) {
        if (since == null || since.value() <= this.a) {
            if (until == null || until.value() > this.a) {
                return true;
            }
        }
        return false;
    }

    @Override // com.google.gson.ExclusionStrategy
    public final boolean shouldSkipClass(Class<?> clazz) {
        return !a((Since) clazz.getAnnotation(Since.class), (Until) clazz.getAnnotation(Until.class));
    }

    @Override // com.google.gson.ExclusionStrategy
    public final boolean shouldSkipField(FieldAttributes f) {
        return !a((Since) f.getAnnotation(Since.class), (Until) f.getAnnotation(Until.class));
    }
}
