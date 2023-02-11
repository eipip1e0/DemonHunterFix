package defpackage;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.webkit.WebView;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.InterstitialAd;
import com.google.gson.Gson;
import defpackage.a;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/* renamed from: c  reason: default package */
public final class c extends AsyncTask<AdRequest, String, AdRequest.ErrorCode> {
    private static final String a;
    private static final String b = ("<html><head><script src=\"" + a + "\"></script><script>");
    private static final Object c = new Object();
    private static long d = 5000;
    private String e;
    private String f = null;
    private b g;
    private d h;
    private WebView i;
    private String j = null;
    private AdRequest.ErrorCode k = null;
    private boolean l = false;
    private boolean m = false;

    /* access modifiers changed from: private */
    /* renamed from: c$a */
    public class a extends Exception {
        public a(String str) {
            super(str);
        }
    }

    static {
        String[] split = AdRequest.VERSION.split("\\.");
        String str = split[0];
        a = "http://www.gstatic.com/afma/sdk-core-v" + str + split[1] + ".js";
    }

    c(d dVar) {
        this.h = dVar;
        Activity c2 = dVar.c();
        if (c2 != null) {
            this.i = new WebView(c2.getApplicationContext());
            this.i.getSettings().setJavaScriptEnabled(true);
            this.i.setWebViewClient(new h(dVar, a.EnumC0000a.URL_REQUEST_TYPE, false));
            this.g = new b(this, dVar, u.a(this.i));
            return;
        }
        this.i = null;
        this.g = null;
        t.e("activity was null while trying to create an AdLoader.");
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public AdRequest.ErrorCode doInBackground(AdRequest... adRequestArr) {
        synchronized (this) {
            if (this.i == null || this.g == null) {
                t.e("adRequestWebView was null while trying to load an ad.");
                return AdRequest.ErrorCode.INTERNAL_ERROR;
            }
            AdRequest adRequest = adRequestArr[0];
            Activity c2 = this.h.c();
            if (c2 == null) {
                t.e("activity was null while forming an ad request.");
                return AdRequest.ErrorCode.INTERNAL_ERROR;
            }
            try {
                Context applicationContext = c2.getApplicationContext();
                Map<String, Object> requestMap = adRequest.getRequestMap();
                f j2 = this.h.j();
                long h2 = j2.h();
                if (h2 > 0) {
                    requestMap.put("prl", Long.valueOf(h2));
                }
                String g2 = j2.g();
                if (g2 != null) {
                    requestMap.put("ppcl", g2);
                }
                String f2 = j2.f();
                if (f2 != null) {
                    requestMap.put("pcl", f2);
                }
                long e2 = j2.e();
                if (e2 > 0) {
                    requestMap.put("pcc", Long.valueOf(e2));
                }
                requestMap.put("preqs", Long.valueOf(f.i()));
                String j3 = j2.j();
                if (j3 != null) {
                    requestMap.put("pai", j3);
                }
                if (j2.k()) {
                    requestMap.put("aoi_timeout", "true");
                }
                if (j2.m()) {
                    requestMap.put("aoi_nofill", "true");
                }
                String p = j2.p();
                if (p != null) {
                    requestMap.put("pit", p);
                }
                j2.a();
                j2.d();
                if (this.h.d() instanceof InterstitialAd) {
                    requestMap.put("format", "interstitial_mb");
                } else {
                    AdSize i2 = this.h.i();
                    String adSize = i2.toString();
                    if (adSize != null) {
                        requestMap.put("format", adSize);
                    } else {
                        HashMap hashMap = new HashMap();
                        hashMap.put("w", Integer.valueOf(i2.getWidth()));
                        hashMap.put("h", Integer.valueOf(i2.getHeight()));
                        requestMap.put("ad_frame", hashMap);
                    }
                }
                requestMap.put("slotname", this.h.f());
                requestMap.put("js", "afma-sdk-a-v4.0.4");
                requestMap.put("msid", applicationContext.getPackageName());
                requestMap.put("app_name", "4.0.4.android." + applicationContext.getPackageName());
                requestMap.put("isu", u.a(applicationContext));
                String c3 = u.c(applicationContext);
                if (c3 == null) {
                    throw new a("NETWORK_ERROR");
                }
                requestMap.put("net", c3);
                String d2 = u.d(applicationContext);
                if (!(d2 == null || d2.length() == 0)) {
                    requestMap.put("cap", d2);
                }
                requestMap.put("u_audio", Integer.valueOf(u.e(applicationContext).ordinal()));
                requestMap.put("u_so", u.f(applicationContext));
                DisplayMetrics a2 = u.a(c2);
                requestMap.put("u_sd", Float.valueOf(a2.density));
                requestMap.put("u_h", Integer.valueOf(a2.heightPixels));
                requestMap.put("u_w", Integer.valueOf(a2.widthPixels));
                requestMap.put("hl", Locale.getDefault().getLanguage());
                if (u.a()) {
                    requestMap.put("simulator", 1);
                }
                String str = b + "AFMA_buildAdURL" + "(" + new Gson().toJson(requestMap) + ");" + "</script></head><body></body></html>";
                t.c("adRequestUrlHtml: " + str);
                this.i.loadDataWithBaseURL(null, str, "text/html", "utf-8", null);
                long b2 = b();
                long elapsedRealtime = SystemClock.elapsedRealtime();
                if (b2 > 0) {
                    try {
                        wait(b2);
                    } catch (InterruptedException e3) {
                        t.e("AdLoader InterruptedException while getting the URL: " + e3);
                        return AdRequest.ErrorCode.INTERNAL_ERROR;
                    }
                }
                if (this.k != null) {
                    return this.k;
                } else if (this.j == null) {
                    t.c("AdLoader timed out while getting the URL.");
                    return AdRequest.ErrorCode.NETWORK_ERROR;
                } else {
                    publishProgress(this.j);
                    long elapsedRealtime2 = b2 - (SystemClock.elapsedRealtime() - elapsedRealtime);
                    if (elapsedRealtime2 > 0) {
                        try {
                            wait(elapsedRealtime2);
                        } catch (InterruptedException e4) {
                            t.e("AdLoader InterruptedException while getting the HTML: " + e4);
                            return AdRequest.ErrorCode.INTERNAL_ERROR;
                        }
                    }
                    if (this.k != null) {
                        return this.k;
                    } else if (this.f == null) {
                        t.c("AdLoader timed out while getting the HTML.");
                        return AdRequest.ErrorCode.NETWORK_ERROR;
                    } else {
                        g g3 = this.h.g();
                        this.h.h().a();
                        g3.loadDataWithBaseURL(this.e, this.f, "text/html", "utf-8", null);
                        long elapsedRealtime3 = b2 - (SystemClock.elapsedRealtime() - elapsedRealtime);
                        if (elapsedRealtime3 > 0) {
                            try {
                                wait(elapsedRealtime3);
                            } catch (InterruptedException e5) {
                                t.e("AdLoader InterruptedException while loading the HTML: " + e5);
                                g3.stopLoading();
                                return AdRequest.ErrorCode.INTERNAL_ERROR;
                            }
                        }
                        if (this.m) {
                            return null;
                        }
                        g3.stopLoading();
                        this.l = true;
                        t.c("AdLoader timed out while loading the HTML.");
                        return AdRequest.ErrorCode.NETWORK_ERROR;
                    }
                }
            } catch (a e6) {
                t.c("Unable to connect to network.");
                return AdRequest.ErrorCode.NETWORK_ERROR;
            }
        }
    }

    public static void a(long j2) {
        synchronized (c) {
            d = j2;
        }
    }

    private long b() {
        long j2;
        if (!(this.h.d() instanceof InterstitialAd)) {
            return 60000;
        }
        synchronized (c) {
            j2 = d;
        }
        return j2;
    }

    /* access modifiers changed from: package-private */
    public final synchronized void a() {
        this.m = true;
        notify();
    }

    public final synchronized void a(AdRequest.ErrorCode errorCode) {
        this.k = errorCode;
        notify();
    }

    public final synchronized void a(String str) {
        this.j = str;
        notify();
    }

    /* access modifiers changed from: package-private */
    public final synchronized void a(String str, String str2) {
        this.e = str2;
        this.f = str;
        notify();
    }

    /* access modifiers changed from: protected */
    public final void onCancelled() {
        t.a("AdLoader cancelled.");
        this.i.stopLoading();
        this.g.cancel(false);
    }

    /* JADX DEBUG: Method arguments types fixed to match base method, original types: [java.lang.Object] */
    /* access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public final /* synthetic */ void onPostExecute(AdRequest.ErrorCode errorCode) {
        AdRequest.ErrorCode errorCode2 = errorCode;
        synchronized (this) {
            if (errorCode2 == null) {
                this.h.n();
            } else {
                this.i.stopLoading();
                this.g.cancel(false);
                if (this.l) {
                    this.h.g().setVisibility(8);
                }
                this.h.a(errorCode2);
            }
        }
    }

    /* JADX DEBUG: Method arguments types fixed to match base method, original types: [java.lang.Object[]] */
    /* access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public final /* synthetic */ void onProgressUpdate(String[] x0) {
        this.g.execute(x0[0]);
    }
}
