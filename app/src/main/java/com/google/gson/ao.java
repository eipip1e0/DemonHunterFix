package com.google.gson;

import java.lang.reflect.Type;

/* access modifiers changed from: package-private */
public final class ao {
    final Type a;
    private Object b;
    private final boolean c;

    ao(Object obj, Type type, boolean z) {
        this.b = obj;
        this.a = type;
        this.c = z;
    }

    /* access modifiers changed from: package-private */
    public final <HANDLER> ap<HANDLER, ao> a(aq<HANDLER> aqVar) {
        if (!this.c && this.b != null) {
            ao b2 = b();
            HANDLER a2 = aqVar.a(b2.a);
            if (a2 != null) {
                return new ap<>(a2, b2);
            }
        }
        HANDLER a3 = aqVar.a(this.a);
        if (a3 == null) {
            return null;
        }
        return new ap<>(a3, this);
    }

    /* access modifiers changed from: package-private */
    public final Object a() {
        return this.b;
    }

    /* access modifiers changed from: package-private */
    public final void a(Object obj) {
        this.b = obj;
    }

    /* access modifiers changed from: package-private */
    public final ao b() {
        Class<?> cls;
        if (this.c || this.b == null) {
            return this;
        }
        Type type = this.a;
        Class<?> cls2 = this.b.getClass();
        if (type instanceof Class) {
            cls = ((Class) type).isAssignableFrom(cls2) ? cls2 : type;
            if (cls == Object.class) {
                cls = cls2;
            }
        } else {
            cls = type;
        }
        return cls == this.a ? this : new ao(this.b, cls, this.c);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ao aoVar = (ao) obj;
        if (this.b == null) {
            if (aoVar.b != null) {
                return false;
            }
        } else if (this.b != aoVar.b) {
            return false;
        }
        if (this.a == null) {
            if (aoVar.a != null) {
                return false;
            }
        } else if (!this.a.equals(aoVar.a)) {
            return false;
        }
        return this.c == aoVar.c;
    }

    public final int hashCode() {
        if (this.b == null) {
            return 31;
        }
        return this.b.hashCode();
    }
}
