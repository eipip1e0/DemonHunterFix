package defpackage;

import android.webkit.WebView;
import com.google.ads.AdActivity;
import java.util.HashMap;

/* renamed from: n  reason: default package */
public final class n implements i {
    @Override // defpackage.i
    public final void a(d dVar, HashMap<String, String> hashMap, WebView webView) {
        String str = hashMap.get("a");
        if (str == null) {
            t.a("Could not get the action parameter for open GMSG.");
        } else if (str.equals("webapp")) {
            AdActivity.launchAdActivity(dVar, new e("webapp", hashMap));
        } else {
            AdActivity.launchAdActivity(dVar, new e("intent", hashMap));
        }
    }
}
