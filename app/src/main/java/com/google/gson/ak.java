package com.google.gson;

import java.util.Collection;
import java.util.HashSet;

final class ak implements ExclusionStrategy {
    private final Collection<Integer> a = new HashSet();

    public ak(int... iArr) {
        if (iArr != null) {
            for (int i : iArr) {
                this.a.add(Integer.valueOf(i));
            }
        }
    }

    @Override // com.google.gson.ExclusionStrategy
    public final boolean shouldSkipClass(Class<?> cls) {
        return false;
    }

    @Override // com.google.gson.ExclusionStrategy
    public final boolean shouldSkipField(FieldAttributes f) {
        for (Integer num : this.a) {
            if (f.hasModifier(num.intValue())) {
                return true;
            }
        }
        return false;
    }
}
