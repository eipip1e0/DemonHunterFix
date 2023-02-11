package com.google.gson;

import java.util.LinkedHashMap;
import java.util.Map;

final class ah<K, V> extends LinkedHashMap<K, V> implements b<K, V> {
    private final int a;

    ah(int i) {
        super(i, 0.7f, true);
        this.a = i;
    }

    @Override // com.google.gson.b
    public final V a(K k) {
        return get(k);
    }

    @Override // com.google.gson.b
    public final void a(K k, V v) {
        put(k, v);
    }

    public final void clear() {
        super.clear();
    }

    /* access modifiers changed from: protected */
    @Override // java.util.LinkedHashMap
    public final boolean removeEldestEntry(Map.Entry<K, V> entry) {
        return size() > this.a;
    }

    public final int size() {
        return super.size();
    }
}
