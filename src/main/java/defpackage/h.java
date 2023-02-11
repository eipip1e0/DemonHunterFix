package defpackage;

import android.net.Uri;
import android.os.AsyncTask;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.google.ads.AdActivity;
import defpackage.a;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

/* renamed from: h  reason: default package */
public final class h extends WebViewClient {
    private d a;
    private a.EnumC0000a b;
    private boolean c;
    private boolean d = false;
    private boolean e = false;

    /* renamed from: h$a */
    private static class a extends AsyncTask<String, Void, Void> {
        private d a;

        public a(d dVar) {
            this.a = dVar;
        }

        /* access modifiers changed from: private */
        /* renamed from: a */
        public Void doInBackground(String... strArr) {
            int responseCode;
            String str;
            String str2 = strArr[0];
            Uri parse = Uri.parse(str2);
            if (!str2.toLowerCase().startsWith("http://") && !str2.toLowerCase().startsWith("https://") && !str2.toLowerCase().startsWith("tel:")) {
                Uri.parse("http://googleads.g.doubleclick.net" + str2);
                str = "http://googleads.g.doubleclick.net" + str2;
            } else if (str2.toLowerCase().startsWith("http://clk") || str2.toLowerCase().startsWith("http://c.admob.com") || str2.toLowerCase().startsWith("http://googleads.g.doubleclick.net/aclk") || str2.toLowerCase().startsWith("http://www.googleadservices.com/pagead/aclk")) {
                if (str2.toLowerCase().startsWith("http://googleads.g.doubleclick.net/aclk")) {
                    HashMap<String, String> a2 = u.a(parse);
                    if (a2 == null) {
                        t.e("An error occurred while parsing the url parameters.");
                        return null;
                    }
                    this.a.j().a(a2.get("ai"));
                }
                while (true) {
                    try {
                        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str2).openConnection();
                        httpURLConnection.setInstanceFollowRedirects(false);
                        responseCode = httpURLConnection.getResponseCode();
                        if (responseCode != 302) {
                            break;
                        }
                        String headerField = httpURLConnection.getHeaderField("Location");
                        if (headerField != null) {
                            Uri.parse(headerField);
                        } else {
                            headerField = str2;
                        }
                        str2 = headerField;
                    } catch (IOException e) {
                        t.b("Unable to check for AdMob redirect.", e);
                        return null;
                    }
                }
                if (responseCode != 200) {
                    t.e("Did not get HTTP_OK response. Response: " + responseCode);
                    return null;
                }
                str = str2;
            } else {
                str = str2;
            }
            if (str.toLowerCase().startsWith("http://market.android.com/details")) {
                str = "market://details" + str.substring("http://market.android.com/details".length());
            }
            HashMap hashMap = new HashMap();
            hashMap.put(AdActivity.URL_PARAM, str);
            AdActivity.launchAdActivity(this.a, new e("intent", hashMap));
            return null;
        }
    }

    public h(d dVar, a.EnumC0000a aVar, boolean z) {
        this.a = dVar;
        this.b = aVar;
        this.c = z;
    }

    /* access modifiers changed from: package-private */
    public final void a() {
        this.d = true;
    }

    public final void b() {
        this.e = true;
    }

    public final void onPageFinished(WebView view, String str) {
        if (this.d) {
            c e2 = this.a.e();
            if (e2 != null) {
                e2.a();
            } else {
                t.a("adLoader was null while trying to setFinishedLoadingHtml().");
            }
            this.d = false;
        }
        if (this.e) {
            a.a(view);
            this.e = false;
        }
    }

    @Override // android.webkit.WebViewClient
    public final boolean shouldOverrideUrlLoading(WebView webView, String url) {
        t.a("shouldOverrideUrlLoading(\"" + url + "\")");
        Uri parse = Uri.parse(url);
        if (a.a(parse)) {
            a.a(this.a, this.b, parse, webView);
            return true;
        } else if (this.c) {
            new a(this.a).execute(url);
            return true;
        } else {
            t.e("URL is not a GMSG and shouldn't launch intents: " + url);
            return true;
        }
    }
}
