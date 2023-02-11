package com.google.gson;

import java.io.IOException;
import java.io.Reader;

/* access modifiers changed from: package-private */
public final class ax {
    int a;
    int b;
    int c;
    public int d;
    protected int[] e;
    protected int[] f;
    protected int g;
    protected int h;
    protected boolean i;
    protected boolean j;
    protected Reader k;
    protected char[] l;
    protected int m;
    protected int n;
    protected int o;

    public ax(Reader reader) {
        this(reader, (byte) 0);
    }

    private ax(Reader reader, byte b2) {
        this.d = -1;
        this.g = 0;
        this.h = 1;
        this.i = false;
        this.j = false;
        this.m = 0;
        this.n = 0;
        this.o = 8;
        this.k = reader;
        this.h = 1;
        this.g = 0;
        this.a = 4096;
        this.b = 4096;
        this.l = new char[4096];
        this.e = new int[4096];
        this.f = new int[4096];
    }

    private void a(boolean z) {
        char[] cArr = new char[(this.a + 2048)];
        int[] iArr = new int[(this.a + 2048)];
        int[] iArr2 = new int[(this.a + 2048)];
        if (z) {
            try {
                System.arraycopy(this.l, this.c, cArr, 0, this.a - this.c);
                System.arraycopy(this.l, 0, cArr, this.a - this.c, this.d);
                this.l = cArr;
                System.arraycopy(this.e, this.c, iArr, 0, this.a - this.c);
                System.arraycopy(this.e, 0, iArr, this.a - this.c, this.d);
                this.e = iArr;
                System.arraycopy(this.f, this.c, iArr2, 0, this.a - this.c);
                System.arraycopy(this.f, 0, iArr2, this.a - this.c, this.d);
                this.f = iArr2;
                int i2 = this.d + (this.a - this.c);
                this.d = i2;
                this.m = i2;
            } catch (Throwable th) {
                throw new Error(th.getMessage());
            }
        } else {
            System.arraycopy(this.l, this.c, cArr, 0, this.a - this.c);
            this.l = cArr;
            System.arraycopy(this.e, this.c, iArr, 0, this.a - this.c);
            this.e = iArr;
            System.arraycopy(this.f, this.c, iArr2, 0, this.a - this.c);
            this.f = iArr2;
            int i3 = this.d - this.c;
            this.d = i3;
            this.m = i3;
        }
        this.a += 2048;
        this.b = this.a;
        this.c = 0;
    }

    public final char a() throws IOException {
        this.c = -1;
        char b2 = b();
        this.c = this.d;
        return b2;
    }

    public final void a(int i2) {
        this.n += i2;
        int i3 = this.d - i2;
        this.d = i3;
        if (i3 < 0) {
            this.d += this.a;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:48:0x00da  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x00dd  */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x00e0  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final char b() throws java.io.IOException {
        /*
        // Method dump skipped, instructions count: 258
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.ax.b():char");
    }

    public final int c() {
        return this.f[this.d];
    }

    public final int d() {
        return this.e[this.d];
    }

    public final int e() {
        return this.f[this.c];
    }

    public final int f() {
        return this.e[this.c];
    }

    public final String g() {
        return this.d >= this.c ? new String(this.l, this.c, (this.d - this.c) + 1) : new String(this.l, this.c, this.a - this.c) + new String(this.l, 0, this.d + 1);
    }
}
