package com.google.gson;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.logging.Logger;

/* access modifiers changed from: package-private */
public final class ai implements am {
    private static final Logger a = Logger.getLogger(ai.class.getName());
    private final aq<InstanceCreator<?>> b;

    public ai(aq<InstanceCreator<?>> aqVar) {
        this.b = aqVar;
    }

    private static <T> T b(Type type) {
        Constructor<?> constructor;
        int i = 0;
        try {
            Constructor<?>[] declaredConstructors = new bb(type).b().getDeclaredConstructors();
            AccessibleObject.setAccessible(declaredConstructors, true);
            int length = declaredConstructors.length;
            while (true) {
                if (i >= length) {
                    constructor = null;
                    break;
                }
                Constructor<?> constructor2 = declaredConstructors[i];
                if (constructor2.getParameterTypes().length == 0) {
                    constructor = constructor2;
                    break;
                }
                i++;
            }
            if (constructor != null) {
                return (T) constructor.newInstance(new Object[0]);
            }
            throw new RuntimeException("No-args constructor for " + type + " does not exist. " + "Register an InstanceCreator with Gson for this type to fix this problem.");
        } catch (InstantiationException e) {
            throw new RuntimeException("Unable to invoke no-args constructor for " + type + ". " + "Register an InstanceCreator with Gson for this type may fix this problem.", e);
        } catch (IllegalAccessException e2) {
            throw new RuntimeException("Unable to invoke no-args constructor for " + type + ". " + "Register an InstanceCreator with Gson for this type may fix this problem.", e2);
        } catch (InvocationTargetException e3) {
            throw new RuntimeException("Unable to invoke no-args constructor for " + type + ". " + "Register an InstanceCreator with Gson for this type may fix this problem.", e3);
        }
    }

    @Override // com.google.gson.am
    public final <T> T a(Type type) {
        InstanceCreator<?> a2 = this.b.a(type);
        return a2 != null ? (T) a2.createInstance(type) : (T) b(type);
    }

    @Override // com.google.gson.am
    public final Object a(Type type, int i) {
        return Array.newInstance(bg.b(type), i);
    }

    public final String toString() {
        return this.b.toString();
    }
}
