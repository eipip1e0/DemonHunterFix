package com.google.gson;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collection;

/* access modifiers changed from: package-private */
public final class ag extends av {
    ag() {
    }

    /* access modifiers changed from: protected */
    @Override // com.google.gson.av
    public final String a(String str, Type type, Collection<Annotation> collection) {
        return str.toLowerCase();
    }
}
