package com.google.gson;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collection;

/* access modifiers changed from: package-private */
public abstract class e extends av {
    private final av[] a;

    public e(av... avVarArr) {
        if (avVarArr == null) {
            throw new NullPointerException("naming policies can not be null.");
        }
        this.a = avVarArr;
    }

    /* access modifiers changed from: protected */
    @Override // com.google.gson.av
    public final String a(String str, Type type, Collection<Annotation> collection) {
        String str2 = str;
        for (av avVar : this.a) {
            str2 = avVar.a(str2, type, collection);
        }
        return str2;
    }
}
