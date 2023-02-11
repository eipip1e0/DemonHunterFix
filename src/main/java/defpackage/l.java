package defpackage;

import android.webkit.WebView;
import java.util.HashMap;

/* renamed from: l  reason: default package */
public final class l implements i {
    @Override // defpackage.i
    public final void a(d dVar, HashMap<String, String> hashMap, WebView webView) {
        String str = hashMap.get("url");
        t.c("Received ad url: <\"url\": \"" + str + "\", \"afmaNotifyDt\": \"" + hashMap.get("afma_notify_dt") + "\">");
        c e = dVar.e();
        if (e != null) {
            e.a(str);
        }
    }
}
