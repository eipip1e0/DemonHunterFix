package com.google.gson;

import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;

/* access modifiers changed from: package-private */
public final class be {
    private be() {
    }

    public static bb a(Field field, Type type) {
        return new bb(a(field.getGenericType(), type, bg.b(type)));
    }

    public static bc a(Type type) {
        at.a(bg.a(type));
        return new bc(type);
    }

    private static Type a(Type type, Type type2, Class<?> cls) {
        while (!(type instanceof Class)) {
            if (type instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                Type ownerType = parameterizedType.getOwnerType();
                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                at.a(actualTypeArguments);
                Type[] typeArr = new Type[actualTypeArguments.length];
                for (int i = 0; i < actualTypeArguments.length; i++) {
                    typeArr[i] = a(actualTypeArguments[i], type2, cls);
                }
                return new ar(parameterizedType.getRawType(), typeArr, ownerType);
            } else if (type instanceof GenericArrayType) {
                GenericArrayType genericArrayType = (GenericArrayType) type;
                Type genericComponentType = genericArrayType.getGenericComponentType();
                Type a = a(genericComponentType, type2, cls);
                return genericComponentType.equals(a) ? genericArrayType : a instanceof Class ? bg.a(bg.b(a)) : new m(a);
            } else if (type instanceof TypeVariable) {
                if (type2 instanceof ParameterizedType) {
                    TypeVariable typeVariable = (TypeVariable) type;
                    TypeVariable<Class<?>>[] typeParameters = cls.getTypeParameters();
                    ParameterizedType parameterizedType2 = (ParameterizedType) type2;
                    for (int i2 = 0; i2 < typeParameters.length; i2++) {
                        if (typeVariable.equals(typeParameters[i2])) {
                            return parameterizedType2.getActualTypeArguments()[i2];
                        }
                    }
                    throw new IllegalStateException("How can the type variable not be present in the class declaration!");
                }
                throw new UnsupportedOperationException("Expecting parameterized type, got " + type2 + ".\n Are you missing the use of TypeToken idiom?\n See " + "http://sites.google.com/site/gson/gson-user-guide#TOC-Serializing-and-Deserializing-Gener");
            } else if (type instanceof WildcardType) {
                type = ((WildcardType) type).getUpperBounds()[0];
            } else {
                throw new IllegalArgumentException("Type '" + type + "' is not a Class, " + "ParameterizedType, GenericArrayType or TypeVariable. Can't extract type.");
            }
        }
        return type;
    }
}
