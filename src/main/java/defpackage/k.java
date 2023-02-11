package defpackage;

import android.webkit.WebView;
import com.google.ads.AdRequest;
import java.util.HashMap;

/* renamed from: k  reason: default package */
public final class k implements i {
    @Override // defpackage.i
    public final void a(d dVar, HashMap<String, String> hashMap, WebView webView) {
        t.e("Invalid " + hashMap.get("type") + " request error: " + hashMap.get("errors"));
        c e = dVar.e();
        if (e != null) {
            e.a(AdRequest.ErrorCode.INVALID_REQUEST);
        }
    }
}
