package com.google.gson;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collection;

final class ModifyFirstLetterNamingPolicy extends av {
    private final LetterModifier a;

    public enum LetterModifier {
        UPPER,
        LOWER
    }

    public ModifyFirstLetterNamingPolicy(LetterModifier modifier) {
        at.a(modifier);
        this.a = modifier;
    }

    private static String a(char c, String str, int i) {
        return i < str.length() ? c + str.substring(i) : String.valueOf(c);
    }

    /* access modifiers changed from: protected */
    @Override // com.google.gson.av
    public final String a(String str, Type type, Collection<Annotation> collection) {
        StringBuilder sb = new StringBuilder();
        char charAt = str.charAt(0);
        int i = 0;
        while (i < str.length() - 1 && !Character.isLetter(charAt)) {
            sb.append(charAt);
            int i2 = i + 1;
            i = i2;
            charAt = str.charAt(i2);
        }
        if (i == str.length()) {
            return sb.toString();
        }
        boolean z = this.a == LetterModifier.UPPER;
        return (!z || Character.isUpperCase(charAt)) ? (z || !Character.isUpperCase(charAt)) ? str : sb.append(a(Character.toLowerCase(charAt), str, i + 1)).toString() : sb.append(a(Character.toUpperCase(charAt), str, i + 1)).toString();
    }
}
