package com.google.gson;

import com.google.gson.ModifyFirstLetterNamingPolicy;

public enum FieldNamingPolicy {
    UPPER_CAMEL_CASE(new ModifyFirstLetterNamingPolicy(ModifyFirstLetterNamingPolicy.LetterModifier.UPPER)),
    UPPER_CAMEL_CASE_WITH_SPACES(new bh(" ")),
    LOWER_CASE_WITH_UNDERSCORES(new af("_")),
    LOWER_CASE_WITH_DASHES(new af("-"));
    
    private final k a;

    private FieldNamingPolicy(k namingPolicy) {
        this.a = namingPolicy;
    }

    /* access modifiers changed from: package-private */
    public final k a() {
        return this.a;
    }
}
