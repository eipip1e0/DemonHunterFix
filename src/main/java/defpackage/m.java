package defpackage;

import android.webkit.WebView;
import java.util.HashMap;

/* renamed from: m  reason: default package */
public final class m implements i {
    @Override // defpackage.i
    public final void a(d dVar, HashMap<String, String> hashMap, WebView webView) {
        t.c("Received log message: <\"string\": \"" + hashMap.get("string") + "\", \"afmaNotifyDt\": \"" + hashMap.get("afma_notify_dt") + "\">");
    }
}
