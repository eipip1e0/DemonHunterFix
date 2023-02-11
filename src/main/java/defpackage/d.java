package defpackage;

import android.app.Activity;
import android.os.Handler;
import com.google.ads.Ad;
import com.google.ads.AdListener;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.google.ads.InterstitialAd;
import defpackage.a;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.LinkedList;

/* renamed from: d  reason: default package */
public final class d {
    private WeakReference<Activity> a;
    private Ad b;
    private AdListener c;
    private c d;
    private AdRequest e;
    private AdSize f;
    private f g;
    private String h;
    private g i;
    private h j;
    private Handler k;
    private boolean l;
    private boolean m;
    private long n;
    private x o;
    private LinkedList<String> p;
    private int q = 4;

    public d(Activity activity, Ad ad, AdSize adSize, String str) {
        this.a = new WeakReference<>(activity);
        this.b = ad;
        this.f = adSize;
        this.h = str;
        this.g = new f();
        this.c = null;
        this.d = null;
        this.e = null;
        this.l = false;
        this.k = new Handler();
        this.n = 0;
        this.m = false;
        this.o = new x(this);
        this.p = new LinkedList<>();
        this.i = new g(activity.getApplicationContext(), adSize);
        u.b(this.i);
        this.i.setBackgroundColor(0);
        this.i.setVisibility(8);
        this.j = new h(this, a.EnumC0000a.AD_TYPE, true);
        this.i.setWebViewClient(this.j);
        u.g(activity.getApplicationContext());
    }

    private synchronized boolean u() {
        return this.d != null;
    }

    public final synchronized void a() {
        if (this.m) {
            t.a("Disabling refreshing.");
            this.k.removeCallbacks(this.o);
            this.m = false;
        } else {
            t.a("Refreshing is already disabled.");
        }
    }

    public final synchronized void a(float f2) {
        this.n = (long) (1000.0f * f2);
    }

    public final void a(int i2) {
        this.q = i2;
    }

    public final synchronized void a(AdListener adListener) {
        this.c = adListener;
    }

    public final synchronized void a(AdRequest.ErrorCode errorCode) {
        this.d = null;
        if (this.b instanceof InterstitialAd) {
            if (errorCode == AdRequest.ErrorCode.NO_FILL) {
                this.g.n();
            } else if (errorCode == AdRequest.ErrorCode.NETWORK_ERROR) {
                this.g.l();
            }
        }
        t.c("onFailedToReceiveAd(" + errorCode + ")");
        if (this.c != null) {
            this.c.onFailedToReceiveAd(this.b, errorCode);
        }
    }

    public final synchronized void a(AdRequest adRequest) {
        if (u()) {
            t.e("loadAd called while the ad is already loading.");
        } else {
            Activity c2 = c();
            if (c2 == null) {
                t.e("activity is null while trying to load an ad.");
            } else if (u.b(c2.getApplicationContext())) {
                this.l = false;
                this.e = adRequest;
                this.d = new c(this);
                this.d.execute(adRequest);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final synchronized void a(String str) {
        this.p.add(str);
    }

    public final synchronized void b() {
        if (!(this.b instanceof AdView)) {
            t.a("Tried to enable refreshing on something other than an AdView.");
        } else if (!this.m) {
            t.a("Enabling refreshing every " + this.n + " milliseconds.");
            this.k.postDelayed(this.o, this.n);
            this.m = true;
        } else {
            t.a("Refreshing is already enabled.");
        }
    }

    public final Activity c() {
        return this.a.get();
    }

    /* access modifiers changed from: package-private */
    public final Ad d() {
        return this.b;
    }

    public final synchronized c e() {
        return this.d;
    }

    /* access modifiers changed from: package-private */
    public final String f() {
        return this.h;
    }

    public final g g() {
        return this.i;
    }

    /* access modifiers changed from: package-private */
    public final h h() {
        return this.j;
    }

    public final AdSize i() {
        return this.f;
    }

    public final f j() {
        return this.g;
    }

    public final int k() {
        return this.q;
    }

    public final synchronized boolean l() {
        return this.l;
    }

    public final synchronized boolean m() {
        return this.m;
    }

    /* access modifiers changed from: package-private */
    public final synchronized void n() {
        this.d = null;
        this.l = true;
        this.i.setVisibility(0);
        this.g.c();
        if (this.b instanceof AdView) {
            r();
        }
        t.c("onReceiveAd()");
        if (this.c != null) {
            this.c.onReceiveAd(this.b);
        }
    }

    public final synchronized void o() {
        this.g.o();
        t.c("onDismissScreen()");
        if (this.c != null) {
            this.c.onDismissScreen(this.b);
        }
    }

    public final synchronized void p() {
        this.g.b();
        t.c("onPresentScreen()");
        if (this.c != null) {
            this.c.onPresentScreen(this.b);
        }
    }

    public final synchronized void q() {
        t.c("onLeaveApplication()");
        if (this.c != null) {
            this.c.onLeaveApplication(this.b);
        }
    }

    public final synchronized void r() {
        Iterator<String> it = this.p.iterator();
        while (it.hasNext()) {
            new w().execute(it.next());
        }
        this.p.clear();
    }

    public final synchronized void s() {
        if (this.e == null) {
            t.a("Tried to refresh before calling loadAd().");
        } else if (this.i.getWindowVisibility() != 0 || !u.b()) {
            t.a("Disabling refreshing because the ad is not visible.");
        } else {
            t.c("Refreshing ad.");
            a(this.e);
            this.k.postDelayed(this.o, this.n);
        }
    }

    public final synchronized void t() {
        if (this.d != null) {
            this.d.cancel(false);
            this.d = null;
        }
        this.i.stopLoading();
    }
}
