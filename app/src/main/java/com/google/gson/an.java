package com.google.gson;

/* access modifiers changed from: package-private */
public final class an {
    private final ExclusionStrategy a;
    private final k b;

    public an(ExclusionStrategy exclusionStrategy, k kVar) {
        at.a(kVar);
        this.a = exclusionStrategy == null ? new al() : exclusionStrategy;
        this.b = kVar;
    }

    public final ObjectNavigator a(ao aoVar) {
        return new ObjectNavigator(aoVar, this.a);
    }

    /* access modifiers changed from: package-private */
    public final k a() {
        return this.b;
    }
}
