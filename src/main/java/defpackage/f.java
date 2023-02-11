package defpackage;

import android.os.SystemClock;
import java.util.LinkedList;

/* renamed from: f  reason: default package */
public final class f {
    private static long e = 0;
    private LinkedList<Long> a = new LinkedList<>();
    private long b;
    private long c;
    private LinkedList<Long> d = new LinkedList<>();
    private String f;
    private boolean g = false;
    private boolean h = false;
    private String i;

    f() {
        a();
    }

    static long i() {
        return e;
    }

    /* access modifiers changed from: package-private */
    public final void a() {
        this.a.clear();
        this.b = 0;
        this.c = 0;
        this.d.clear();
        this.f = null;
        this.g = false;
        this.h = false;
    }

    /* access modifiers changed from: package-private */
    public final void a(String str) {
        t.d("Prior ad identifier = " + str);
        this.f = str;
    }

    /* access modifiers changed from: package-private */
    public final void b() {
        t.d("Ad clicked.");
        this.a.add(Long.valueOf(SystemClock.elapsedRealtime()));
    }

    public final void b(String str) {
        t.d("Prior impression ticket = " + str);
        this.i = str;
    }

    /* access modifiers changed from: package-private */
    public final void c() {
        t.d("Ad request loaded.");
        this.b = SystemClock.elapsedRealtime();
    }

    /* access modifiers changed from: package-private */
    public final void d() {
        t.d("Ad request started.");
        this.c = SystemClock.elapsedRealtime();
        e++;
    }

    /* access modifiers changed from: package-private */
    public final long e() {
        if (this.a.size() != this.d.size()) {
            return -1;
        }
        return (long) this.a.size();
    }

    /* access modifiers changed from: package-private */
    public final String f() {
        if (this.a.isEmpty() || this.a.size() != this.d.size()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < this.a.size(); i2++) {
            if (i2 != 0) {
                sb.append(",");
            }
            sb.append(Long.toString(this.d.get(i2).longValue() - this.a.get(i2).longValue()));
        }
        return sb.toString();
    }

    /* access modifiers changed from: package-private */
    public final String g() {
        if (this.a.isEmpty()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < this.a.size(); i2++) {
            if (i2 != 0) {
                sb.append(",");
            }
            sb.append(Long.toString(this.a.get(i2).longValue() - this.b));
        }
        return sb.toString();
    }

    /* access modifiers changed from: package-private */
    public final long h() {
        return this.b - this.c;
    }

    /* access modifiers changed from: package-private */
    public final String j() {
        return this.f;
    }

    /* access modifiers changed from: package-private */
    public final boolean k() {
        return this.g;
    }

    /* access modifiers changed from: package-private */
    public final void l() {
        t.d("Interstitial network error.");
        this.g = true;
    }

    /* access modifiers changed from: package-private */
    public final boolean m() {
        return this.h;
    }

    /* access modifiers changed from: package-private */
    public final void n() {
        t.d("Interstitial no fill.");
        this.h = true;
    }

    public final void o() {
        t.d("Landing page dismissed.");
        this.d.add(Long.valueOf(SystemClock.elapsedRealtime()));
    }

    /* access modifiers changed from: package-private */
    public final String p() {
        return this.i;
    }
}
