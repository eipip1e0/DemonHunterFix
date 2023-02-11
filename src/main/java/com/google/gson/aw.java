package com.google.gson;

import com.google.gson.annotations.SerializedName;

final class aw implements k {
    private static final v a = new v();
    private final k b;

    public aw(k kVar) {
        this.b = kVar;
    }

    @Override // com.google.gson.k
    public final String a(FieldAttributes fieldAttributes) {
        at.a(fieldAttributes);
        SerializedName serializedName = (SerializedName) fieldAttributes.getAnnotation(SerializedName.class);
        if (serializedName == null) {
            return this.b.a(fieldAttributes);
        }
        v vVar = a;
        return v.a(serializedName.value());
    }
}
