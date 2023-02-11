package defpackage;

import android.net.Uri;
import android.webkit.WebView;
import com.google.ads.AdActivity;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* renamed from: a  reason: default package */
public final class a {
    private static final Map<String, i> a;
    private static final Map<String, i> b;

    /* renamed from: a$a  reason: collision with other inner class name */
    public enum EnumC0000a {
        URL_REQUEST_TYPE,
        AD_TYPE
    }

    static {
        HashMap hashMap = new HashMap();
        hashMap.put("/invalidRequest", new k());
        hashMap.put("/loadAdURL", new l());
        a = Collections.unmodifiableMap(hashMap);
        HashMap hashMap2 = new HashMap();
        hashMap2.put("/open", new n());
        hashMap2.put("/close", new j());
        hashMap2.put("/log", new m());
        hashMap2.put("/click", new o());
        hashMap2.put("/httpTrack", new o());
        hashMap2.put("/reloadRequest", new p());
        hashMap2.put("/settings", new q());
        hashMap2.put("/touch", new r());
        hashMap2.put("/video", new s());
        b = Collections.unmodifiableMap(hashMap2);
    }

    private a() {
    }

    public static void a(WebView webView) {
        t.d("Calling onshow.");
        webView.loadUrl("javascript:AFMA_ReceiveMessage('onshow', {'version': 'afma-sdk-a-v4.0.4'});");
    }

    static void a(d dVar, EnumC0000a aVar, Uri uri, WebView webView) {
        String path;
        i iVar;
        HashMap<String, String> a2 = u.a(uri);
        if (a2 == null) {
            t.e("An error occurred while parsing the message parameters.");
            return;
        }
        if (c(uri)) {
            String host = uri.getHost();
            if (host == null) {
                t.e("An error occurred while parsing the AMSG parameters.");
                return;
            } else if (host.equals("launch")) {
                a2.put("a", "intent");
                a2.put(AdActivity.URL_PARAM, a2.get("url"));
                a2.remove("url");
                path = "/open";
            } else if (host.equals("closecanvas")) {
                path = "/close";
            } else if (host.equals("log")) {
                path = "/log";
            } else {
                t.e("An error occurred while parsing the AMSG: " + uri.toString());
                return;
            }
        } else {
            path = b(uri) ? uri.getPath() : null;
        }
        switch (aVar) {
            case URL_REQUEST_TYPE:
                iVar = a.get(path);
                break;
            case AD_TYPE:
                iVar = b.get(path);
                break;
            default:
                iVar = null;
                break;
        }
        if (iVar == null) {
            t.e("No AdResponse found, <message: " + path + ">");
        } else {
            iVar.a(dVar, a2, webView);
        }
    }

    static boolean a(Uri uri) {
        if (uri == null || !uri.isHierarchical()) {
            return false;
        }
        return b(uri) || c(uri);
    }

    public static void b(WebView webView) {
        t.d("Calling onhide.");
        webView.loadUrl("javascript:AFMA_ReceiveMessage('onhide');");
    }

    private static boolean b(Uri uri) {
        String scheme = uri.getScheme();
        if (scheme == null || !scheme.equals("gmsg")) {
            return false;
        }
        String authority = uri.getAuthority();
        return authority != null && authority.equals("mobileads.google.com");
    }

    private static boolean c(Uri uri) {
        String scheme = uri.getScheme();
        return scheme != null && scheme.equals("admob");
    }
}
