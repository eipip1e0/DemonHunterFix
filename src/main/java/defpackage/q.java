package defpackage;

import android.webkit.WebView;
import java.util.HashMap;

/* renamed from: q  reason: default package */
public final class q implements i {
    @Override // defpackage.i
    public final void a(d dVar, HashMap<String, String> hashMap, WebView webView) {
        String str = hashMap.get("applicationTimeout");
        try {
            c.a((long) (Float.parseFloat(str) * 1000.0f));
        } catch (NumberFormatException e) {
            t.b("Trying to set applicationTimeout to invalid value: " + str, e);
        }
    }
}
