package com.google.gson;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collection;

/* access modifiers changed from: package-private */
public abstract class av implements k {
    av() {
    }

    @Override // com.google.gson.k
    public final String a(FieldAttributes fieldAttributes) {
        at.a(fieldAttributes);
        return a(fieldAttributes.getName(), fieldAttributes.getDeclaredType(), fieldAttributes.getAnnotations());
    }

    /* access modifiers changed from: protected */
    public abstract String a(String str, Type type, Collection<Annotation> collection);
}
