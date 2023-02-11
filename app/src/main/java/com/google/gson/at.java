package com.google.gson;

/* access modifiers changed from: package-private */
public final class at {
    at() {
    }

    public static void a(Object obj) {
        a(obj != null);
    }

    public static void a(boolean z) {
        if (!z) {
            throw new IllegalArgumentException("condition failed: " + z);
        }
    }
}
