package com.google.gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/* access modifiers changed from: package-private */
public final class aq<T> {
    private static final Logger a = Logger.getLogger(aq.class.getName());
    private final Map<Type, T> b = new HashMap();
    private final List<ap<Class<?>, T>> c = new ArrayList();
    private boolean d = true;

    aq() {
    }

    private int a(Class<?> cls) {
        for (int size = this.c.size() - 1; size >= 0; size--) {
            if (cls.isAssignableFrom(this.c.get(size).a)) {
                return size;
            }
        }
        return -1;
    }

    private synchronized void a(ap<Class<?>, T> apVar) {
        if (!this.d) {
            throw new IllegalStateException("Attempted to modify an unmodifiable map.");
        }
        int b2 = b((Class<?>) apVar.a);
        if (b2 >= 0) {
            a.log(Level.WARNING, "Overriding the existing type handler for {0}", (Object) apVar.a);
            this.c.remove(b2);
        }
        int a2 = a((Class<?>) apVar.a);
        if (a2 >= 0) {
            throw new IllegalArgumentException("The specified type handler for type " + ((Object) apVar.a) + " hides the previously registered type hierarchy handler for " + ((Object) this.c.get(a2).a) + ". Gson does not allow this.");
        }
        this.c.add(0, apVar);
    }

    private synchronized int b(Class<?> cls) {
        int i;
        int size = this.c.size() - 1;
        while (true) {
            if (size < 0) {
                i = -1;
                break;
            } else if (cls.equals(this.c.get(size).a)) {
                i = size;
                break;
            } else {
                size--;
            }
        }
        return i;
    }

    public final synchronized T a(Type type) {
        SECOND second;
        second = this.b.get(type);
        if (second == null) {
            Class<?> b2 = bg.b(type);
            if (b2 != type) {
                second = a((Type) b2);
            }
            if (second == null) {
                Iterator<ap<Class<?>, T>> it = this.c.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        second = (T) null;
                        break;
                    }
                    ap<Class<?>, T> next = it.next();
                    if (next.a.isAssignableFrom(b2)) {
                        second = next.b;
                        break;
                    }
                }
            }
        }
        return (T) second;
    }

    public final synchronized void a() {
        this.d = false;
    }

    public final synchronized void a(aq<T> aqVar) {
        if (!this.d) {
            throw new IllegalStateException("Attempted to modify an unmodifiable map.");
        }
        for (Map.Entry<Type, T> entry : aqVar.b.entrySet()) {
            if (!this.b.containsKey(entry.getKey())) {
                a(entry.getKey(), entry.getValue());
            }
        }
        for (int size = aqVar.c.size() - 1; size >= 0; size--) {
            ap<Class<?>, T> apVar = aqVar.c.get(size);
            if (b(apVar.a) < 0) {
                a(apVar);
            }
        }
    }

    public final synchronized void a(Class<?> cls, T t) {
        a(new ap<>(cls, t));
    }

    public final synchronized void a(Type type, T t) {
        if (!this.d) {
            throw new IllegalStateException("Attempted to modify an unmodifiable map.");
        }
        if (b(type)) {
            a.log(Level.WARNING, "Overriding the existing type handler for {0}", type);
        }
        this.b.put(type, t);
    }

    public final synchronized aq<T> b() {
        aq<T> aqVar;
        aqVar = new aq<>();
        for (Map.Entry<Type, T> entry : this.b.entrySet()) {
            aqVar.a(entry.getKey(), entry.getValue());
        }
        for (ap<Class<?>, T> apVar : this.c) {
            aqVar.a(apVar);
        }
        return aqVar;
    }

    public final synchronized void b(Type type, T t) {
        if (!this.d) {
            throw new IllegalStateException("Attempted to modify an unmodifiable map.");
        } else if (!this.b.containsKey(type)) {
            a(type, t);
        }
    }

    public final synchronized boolean b(Type type) {
        return this.b.containsKey(type);
    }

    public final String toString() {
        boolean z;
        boolean z2;
        StringBuilder sb = new StringBuilder("{mapForTypeHierarchy:{");
        boolean z3 = true;
        for (ap<Class<?>, T> apVar : this.c) {
            if (z3) {
                z2 = false;
            } else {
                sb.append(',');
                z2 = z3;
            }
            sb.append(bg.b(apVar.a).getSimpleName()).append(':');
            sb.append((Object) apVar.b);
            z3 = z2;
        }
        sb.append("},map:{");
        boolean z4 = true;
        for (Map.Entry<Type, T> entry : this.b.entrySet()) {
            if (z4) {
                z = false;
            } else {
                sb.append(',');
                z = z4;
            }
            sb.append(bg.b(entry.getKey()).getSimpleName()).append(':');
            sb.append((Object) entry.getValue());
            z4 = z;
        }
        sb.append("}");
        return sb.toString();
    }
}
