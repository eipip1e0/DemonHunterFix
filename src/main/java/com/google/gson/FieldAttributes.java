package com.google.gson;

import com.feelingk.iap.IAPLib;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

public final class FieldAttributes {
    private static final b<ap<Class<?>, String>, Collection<Annotation>> a = new ah(c());
    private final Class<?> b;
    private final Field c;
    private final Class<?> d;
    private final boolean e;
    private final int f;
    private final String g;
    private Type h;
    private Collection<Annotation> i;

    FieldAttributes(Class<?> parentClazz, Field f2) {
        at.a(parentClazz);
        this.b = parentClazz;
        this.g = f2.getName();
        this.d = f2.getType();
        this.e = f2.isSynthetic();
        this.f = f2.getModifiers();
        this.c = f2;
    }

    private static int c() {
        try {
            return Integer.parseInt(System.getProperty("com.google.gson.annotation_cache_size_hint", String.valueOf((int) IAPLib.HND_ERR_AUTH)));
        } catch (NumberFormatException e2) {
            return IAPLib.HND_ERR_AUTH;
        }
    }

    /* access modifiers changed from: package-private */
    public final Object a(Object obj) throws IllegalAccessException {
        return this.c.get(obj);
    }

    /* access modifiers changed from: package-private */
    public final void a(Object obj, Object obj2) throws IllegalAccessException {
        this.c.set(obj, obj2);
    }

    /* access modifiers changed from: package-private */
    public final boolean a() {
        return this.e;
    }

    /* access modifiers changed from: package-private */
    public final Field b() {
        return this.c;
    }

    public final <T extends Annotation> T getAnnotation(Class<T> annotation) {
        Iterator<Annotation> it = getAnnotations().iterator();
        while (it.hasNext()) {
            T t = (T) it.next();
            if (t.annotationType() == annotation) {
                return t;
            }
        }
        return null;
    }

    public final Collection<Annotation> getAnnotations() {
        if (this.i == null) {
            ap<Class<?>, String> apVar = new ap<>(this.b, this.g);
            this.i = a.a(apVar);
            if (this.i == null) {
                this.i = Collections.unmodifiableCollection(Arrays.asList(this.c.getAnnotations()));
                a.a(apVar, this.i);
            }
        }
        return this.i;
    }

    public final Class<?> getDeclaredClass() {
        return this.d;
    }

    public final Type getDeclaredType() {
        if (this.h == null) {
            this.h = this.c.getGenericType();
        }
        return this.h;
    }

    public final String getName() {
        return this.g;
    }

    public final boolean hasModifier(int modifier) {
        return (this.f & modifier) != 0;
    }
}
