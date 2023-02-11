package defpackage;

import android.net.Uri;
import android.webkit.WebView;
import com.google.ads.AdActivity;
import java.util.HashMap;

/* renamed from: o  reason: default package */
public final class o implements i {
    @Override // defpackage.i
    public final void a(d dVar, HashMap<String, String> hashMap, WebView webView) {
        String str = hashMap.get(AdActivity.URL_PARAM);
        f j = dVar.j();
        if (j != null) {
            Uri parse = Uri.parse(str);
            if (parse.getHost().endsWith("admob.com")) {
                j.b(parse.getPath().substring(4));
            }
        }
        new w().execute(str);
    }
}
