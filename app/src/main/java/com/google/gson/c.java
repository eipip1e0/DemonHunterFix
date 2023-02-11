package com.google.gson;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collection;

/* access modifiers changed from: package-private */
public final class c extends av {
    private final String a;

    public c(String str) {
        at.a(str);
        at.a(!"".equals(str));
        this.a = str;
    }

    /* access modifiers changed from: protected */
    @Override // com.google.gson.av
    public final String a(String str, Type type, Collection<Annotation> collection) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (Character.isUpperCase(charAt) && sb.length() != 0) {
                sb.append(this.a);
            }
            sb.append(charAt);
        }
        return sb.toString();
    }
}
