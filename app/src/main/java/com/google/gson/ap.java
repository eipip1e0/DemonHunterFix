package com.google.gson;

/* access modifiers changed from: package-private */
public final class ap<FIRST, SECOND> {
    final FIRST a;
    final SECOND b;

    ap(FIRST first, SECOND second) {
        this.a = first;
        this.b = second;
    }

    private static boolean a(Object obj, Object obj2) {
        return obj == obj2 || (obj != null && obj.equals(obj2));
    }

    public final boolean equals(Object o) {
        if (!(o instanceof ap)) {
            return false;
        }
        ap apVar = (ap) o;
        return a(this.a, apVar.a) && a(this.b, apVar.b);
    }

    public final int hashCode() {
        return ((this.a != null ? this.a.hashCode() : 0) * 17) + ((this.b != null ? this.b.hashCode() : 0) * 17);
    }

    public final String toString() {
        return String.format("{%s,%s}", this.a, this.b);
    }
}
