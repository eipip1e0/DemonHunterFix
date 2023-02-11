package defpackage;

import android.webkit.WebView;
import java.util.HashMap;

/* renamed from: j  reason: default package */
public final class j implements i {
    @Override // defpackage.i
    public final void a(d dVar, HashMap<String, String> hashMap, WebView webView) {
        if (webView instanceof g) {
            ((g) webView).a();
        } else {
            t.b("Trying to close WebView that isn't an AdWebView");
        }
    }
}
