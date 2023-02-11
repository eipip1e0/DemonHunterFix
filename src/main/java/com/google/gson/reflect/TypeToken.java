package com.google.gson.reflect;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.HashMap;
import java.util.Map;

public abstract class TypeToken<T> {
    final Class<? super T> a;
    final Type b;

    private static class a<T> extends TypeToken<T> {
        public a(Type type) {
            super(type);
        }
    }

    protected TypeToken() {
        Type genericSuperclass = getClass().getGenericSuperclass();
        if (genericSuperclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        this.b = ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
        this.a = (Class<? super T>) a(this.b);
    }

    /* synthetic */ TypeToken(Type x0) {
        this(x0, (byte) 0);
    }

    private TypeToken(Type type, byte b2) {
        if (type == null) {
            throw new NullPointerException("type");
        }
        this.a = (Class<? super T>) a(type);
        this.b = type;
    }

    private static AssertionError a(Type type, Class<?>... clsArr) {
        StringBuilder sb = new StringBuilder("Unexpected type. Expected one of: ");
        for (Class<?> cls : clsArr) {
            sb.append(cls.getName()).append(", ");
        }
        sb.append("but got: ").append(type.getClass().getName()).append(", for type token: ").append(type.toString()).append('.');
        return new AssertionError(sb.toString());
    }

    private static Class<?> a(Type type) {
        if (type instanceof Class) {
            return (Class) type;
        }
        if (type instanceof ParameterizedType) {
            Type rawType = ((ParameterizedType) type).getRawType();
            if (rawType instanceof Class) {
                return (Class) rawType;
            }
            throw a(rawType, Class.class);
        } else if (type instanceof GenericArrayType) {
            return Array.newInstance(a(((GenericArrayType) type).getGenericComponentType()), 0).getClass();
        } else {
            throw a(type, ParameterizedType.class, GenericArrayType.class);
        }
    }

    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:14:0x0012 */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v3 */
    /* JADX WARN: Type inference failed for: r2v5, types: [java.lang.Class] */
    /* JADX WARN: Type inference failed for: r2v7, types: [java.lang.reflect.Type] */
    /* JADX WARN: Type inference failed for: r2v8, types: [java.lang.reflect.Type] */
    /* JADX WARN: Type inference failed for: r2v9 */
    private static boolean a(Type type, GenericArrayType genericArrayType) {
        Class cls;
        Type genericComponentType = genericArrayType.getGenericComponentType();
        if (!(genericComponentType instanceof ParameterizedType)) {
            return true;
        }
        if (type instanceof GenericArrayType) {
            cls = ((GenericArrayType) type).getGenericComponentType();
        } else if (type instanceof Class) {
            cls = (Class) type;
            while (cls.isArray()) {
                cls = cls.getComponentType();
            }
        } else {
            cls = type;
        }
        return a(cls, (ParameterizedType) genericComponentType, new HashMap());
    }

    private static boolean a(Type type, ParameterizedType parameterizedType, Map<String, Type> map) {
        boolean z;
        if (type == null) {
            return false;
        }
        if (parameterizedType.equals(type)) {
            return true;
        }
        Class<?> a2 = a(type);
        ParameterizedType parameterizedType2 = null;
        if (type instanceof ParameterizedType) {
            parameterizedType2 = (ParameterizedType) type;
        }
        if (parameterizedType2 != null) {
            Type[] actualTypeArguments = parameterizedType2.getActualTypeArguments();
            TypeVariable<Class<?>>[] typeParameters = a2.getTypeParameters();
            for (int i = 0; i < actualTypeArguments.length; i++) {
                Type type2 = actualTypeArguments[i];
                TypeVariable<Class<?>> typeVariable = typeParameters[i];
                Type type3 = type2;
                while (type3 instanceof TypeVariable) {
                    type3 = map.get(((TypeVariable) type3).getName());
                }
                map.put(typeVariable.getName(), type3);
            }
            if (parameterizedType2.getRawType().equals(parameterizedType.getRawType())) {
                Type[] actualTypeArguments2 = parameterizedType2.getActualTypeArguments();
                Type[] actualTypeArguments3 = parameterizedType.getActualTypeArguments();
                int i2 = 0;
                while (true) {
                    if (i2 >= actualTypeArguments2.length) {
                        z = true;
                        break;
                    }
                    Type type4 = actualTypeArguments2[i2];
                    Type type5 = actualTypeArguments3[i2];
                    if (!(type5.equals(type4) ? true : type4 instanceof TypeVariable ? type5.equals(map.get(((TypeVariable) type4).getName())) : false)) {
                        break;
                    }
                    i2++;
                }
            }
            z = false;
            if (z) {
                return true;
            }
        }
        for (Type type6 : a2.getGenericInterfaces()) {
            if (a(type6, parameterizedType, new HashMap(map))) {
                return true;
            }
        }
        return a(a2.getGenericSuperclass(), parameterizedType, new HashMap(map));
    }

    public static <T> TypeToken<T> get(Class<T> type) {
        return new a(type);
    }

    public static TypeToken<?> get(Type type) {
        return new a(type);
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TypeToken)) {
            return false;
        }
        return this.b.equals(((TypeToken) o).b);
    }

    public Class<? super T> getRawType() {
        return this.a;
    }

    public Type getType() {
        return this.b;
    }

    public int hashCode() {
        return this.b.hashCode();
    }

    public boolean isAssignableFrom(TypeToken<?> token) {
        return isAssignableFrom(token.getType());
    }

    public boolean isAssignableFrom(Class<?> cls) {
        return isAssignableFrom((Type) cls);
    }

    public boolean isAssignableFrom(Type from) {
        if (from == null) {
            return false;
        }
        if (this.b.equals(from)) {
            return true;
        }
        if (this.b instanceof Class) {
            return this.a.isAssignableFrom(a(from));
        }
        if (this.b instanceof ParameterizedType) {
            return a(from, (ParameterizedType) this.b, new HashMap());
        }
        if (this.b instanceof GenericArrayType) {
            return this.a.isAssignableFrom(a(from)) && a(from, (GenericArrayType) this.b);
        }
        throw a(this.b, Class.class, ParameterizedType.class, GenericArrayType.class);
    }

    public String toString() {
        return this.b instanceof Class ? ((Class) this.b).getName() : this.b.toString();
    }
}
