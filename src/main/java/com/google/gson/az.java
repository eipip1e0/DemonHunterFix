package com.google.gson;

import java.io.Serializable;

/* access modifiers changed from: package-private */
public final class az implements Serializable {
    public int a;
    public int b;
    public int c;
    public int d;
    public int e;
    public String f;
    public az g;

    public az() {
    }

    private az(int i, String str) {
        this.a = i;
        this.f = str;
    }

    public static az a(int i, String str) {
        return new az(i, str);
    }

    public final String toString() {
        return this.f;
    }
}
