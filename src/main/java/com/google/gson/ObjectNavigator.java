package com.google.gson;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Type;

/* access modifiers changed from: package-private */
public final class ObjectNavigator {
    private final ExclusionStrategy a;
    private final ao b;

    public interface Visitor {
        void end(ao aoVar);

        Object getTarget();

        void start(ao aoVar);

        void startVisitingObject(Object obj);

        void visitArray(Object obj, Type type);

        void visitArrayField(FieldAttributes fieldAttributes, Type type, Object obj);

        boolean visitFieldUsingCustomHandler(FieldAttributes fieldAttributes, Type type, Object obj);

        void visitObjectField(FieldAttributes fieldAttributes, Type type, Object obj);

        void visitPrimitive(Object obj);

        boolean visitUsingCustomHandler(ao aoVar);
    }

    ObjectNavigator(ao objTypePair, ExclusionStrategy exclusionStrategy) {
        at.a(exclusionStrategy);
        this.b = objTypePair;
        this.a = exclusionStrategy;
    }

    public final void a(Visitor visitor) {
        bb bbVar = new bb(this.b.a);
        if (!this.a.shouldSkipClass(bbVar.b()) && !visitor.visitUsingCustomHandler(this.b)) {
            Object a2 = this.b.a();
            if (a2 == null) {
                a2 = visitor.getTarget();
            }
            if (a2 != null) {
                this.b.a(a2);
                visitor.start(this.b);
                try {
                    if (bbVar.c()) {
                        visitor.visitArray(a2, this.b.a);
                    } else {
                        if (bbVar.a() == Object.class) {
                            Class<?> cls = a2.getClass();
                            if (cls == Object.class || cls == String.class || au.c(cls).isPrimitive()) {
                                visitor.visitPrimitive(a2);
                                visitor.getTarget();
                            }
                        }
                        visitor.startVisitingObject(a2);
                        Class<?> b2 = new bb(this.b.b().a).b();
                        while (b2 != null && !b2.equals(Object.class)) {
                            if (!b2.isSynthetic()) {
                                Field[] declaredFields = b2.getDeclaredFields();
                                AccessibleObject.setAccessible(declaredFields, true);
                                for (Field field : declaredFields) {
                                    FieldAttributes fieldAttributes = new FieldAttributes(b2, field);
                                    if (!this.a.shouldSkipField(fieldAttributes) && !this.a.shouldSkipClass(fieldAttributes.getDeclaredClass())) {
                                        bb a3 = be.a(field, this.b.a);
                                        Type a4 = a3.a();
                                        if (!visitor.visitFieldUsingCustomHandler(fieldAttributes, a4, a2)) {
                                            if (a3.c()) {
                                                visitor.visitArrayField(fieldAttributes, a4, a2);
                                            } else {
                                                visitor.visitObjectField(fieldAttributes, a4, a2);
                                            }
                                        }
                                    }
                                }
                            }
                            b2 = b2.getSuperclass();
                        }
                    }
                } finally {
                    visitor.end(this.b);
                }
            }
        }
    }
}
