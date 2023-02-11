package com.google.gson;

import com.google.gson.f;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;

public final class GsonBuilder {
    private static final n a = new n();
    private static final j b = new j();
    private static final i c = new i();
    private final Collection<ExclusionStrategy> d = new HashSet();
    private double e;
    private ak f;
    private boolean g;
    private boolean h;
    private LongSerializationPolicy i;
    private k j;
    private final aq<InstanceCreator<?>> k;
    private final aq<JsonSerializer<?>> l;
    private final aq<JsonDeserializer<?>> m;
    private boolean n;
    private String o;
    private int p;
    private int q;
    private boolean r;
    private boolean s;
    private boolean t;
    private boolean u;

    public GsonBuilder() {
        this.d.add(Gson.a);
        this.d.add(Gson.b);
        this.e = -1.0d;
        this.g = true;
        this.t = false;
        this.s = true;
        this.f = Gson.c;
        this.h = false;
        this.i = LongSerializationPolicy.DEFAULT;
        this.j = Gson.e;
        this.k = new aq<>();
        this.l = new aq<>();
        this.m = new aq<>();
        this.n = false;
        this.p = 2;
        this.q = 2;
        this.r = false;
        this.u = false;
    }

    private GsonBuilder a(k kVar) {
        this.j = new aw(kVar);
        return this;
    }

    public final Gson create() {
        LinkedList linkedList = new LinkedList(this.d);
        LinkedList linkedList2 = new LinkedList(this.d);
        linkedList.add(this.f);
        linkedList2.add(this.f);
        if (!this.g) {
            linkedList.add(a);
            linkedList2.add(a);
        }
        if (this.e != -1.0d) {
            linkedList.add(new bi(this.e));
            linkedList2.add(new bi(this.e));
        }
        if (this.h) {
            linkedList.add(b);
            linkedList2.add(c);
        }
        g gVar = new g(linkedList);
        g gVar2 = new g(linkedList2);
        aq<JsonSerializer<?>> b2 = this.l.b();
        aq<JsonDeserializer<?>> b3 = this.m.b();
        String str = this.o;
        int i2 = this.p;
        int i3 = this.q;
        f.g gVar3 = (str == null || "".equals(str.trim())) ? (i2 == 2 || i3 == 2) ? null : new f.g(i2, i3) : new f.g(str);
        if (gVar3 != null) {
            if (!b2.b((Type) Date.class)) {
                b2.a((Type) Date.class, (JsonSerializer<?>) gVar3);
            }
            if (!b3.b((Type) Date.class)) {
                b3.a((Type) Date.class, (JsonDeserializer<?>) gVar3);
            }
        }
        b2.a(f.a(this.r, this.i));
        b3.a(f.b());
        aq<InstanceCreator<?>> b4 = this.k.b();
        b4.a(f.c());
        b2.a();
        b3.a();
        this.k.a();
        return new Gson(gVar, gVar2, this.j, new ai(b4), this.t ? new ab(this.s, (byte) 0) : new q(this.s), this.n, b2, b3, this.u);
    }

    public final GsonBuilder disableHtmlEscaping() {
        this.s = false;
        return this;
    }

    public final GsonBuilder disableInnerClassSerialization() {
        this.g = false;
        return this;
    }

    public final GsonBuilder excludeFieldsWithModifiers(int... modifiers) {
        this.f = new ak(modifiers);
        return this;
    }

    public final GsonBuilder excludeFieldsWithoutExposeAnnotation() {
        this.h = true;
        return this;
    }

    public final GsonBuilder generateNonExecutableJson() {
        this.u = true;
        return this;
    }

    public final GsonBuilder registerTypeAdapter(Type type, Object typeAdapter) {
        at.a((typeAdapter instanceof JsonSerializer) || (typeAdapter instanceof JsonDeserializer) || (typeAdapter instanceof InstanceCreator));
        if (typeAdapter instanceof InstanceCreator) {
            this.k.a(type, (InstanceCreator) typeAdapter);
        }
        if (typeAdapter instanceof JsonSerializer) {
            this.l.a(type, (JsonSerializer) typeAdapter);
        }
        if (typeAdapter instanceof JsonDeserializer) {
            this.m.a(type, new t((JsonDeserializer) typeAdapter));
        }
        return this;
    }

    public final GsonBuilder serializeNulls() {
        this.n = true;
        return this;
    }

    public final GsonBuilder serializeSpecialFloatingPointValues() {
        this.r = true;
        return this;
    }

    public final GsonBuilder setDateFormat(int style) {
        this.p = style;
        this.o = null;
        return this;
    }

    public final GsonBuilder setDateFormat(int dateStyle, int timeStyle) {
        this.p = dateStyle;
        this.q = timeStyle;
        this.o = null;
        return this;
    }

    public final GsonBuilder setDateFormat(String pattern) {
        this.o = pattern;
        return this;
    }

    public final GsonBuilder setExclusionStrategies(ExclusionStrategy... strategies) {
        for (ExclusionStrategy exclusionStrategy : strategies) {
            this.d.add(exclusionStrategy);
        }
        return this;
    }

    public final GsonBuilder setFieldNamingPolicy(FieldNamingPolicy namingConvention) {
        return a(namingConvention.a());
    }

    public final GsonBuilder setFieldNamingStrategy(FieldNamingStrategy fieldNamingStrategy) {
        return a(new l(fieldNamingStrategy));
    }

    public final GsonBuilder setLongSerializationPolicy(LongSerializationPolicy serializationPolicy) {
        this.i = serializationPolicy;
        return this;
    }

    public final GsonBuilder setPrettyPrinting() {
        this.t = true;
        return this;
    }

    public final GsonBuilder setVersion(double ignoreVersionsAfter) {
        this.e = ignoreVersionsAfter;
        return this;
    }
}
