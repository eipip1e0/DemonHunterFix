package com.google.gson;

final class l implements k {
    private final FieldNamingStrategy a;

    public l(FieldNamingStrategy fieldNamingStrategy) {
        at.a(fieldNamingStrategy);
        this.a = fieldNamingStrategy;
    }

    @Override // com.google.gson.k
    public final String a(FieldAttributes fieldAttributes) {
        return this.a.translateName(fieldAttributes.b());
    }
}
