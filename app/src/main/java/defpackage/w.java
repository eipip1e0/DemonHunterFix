package defpackage;

import android.os.AsyncTask;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/* renamed from: w  reason: default package */
public final class w extends AsyncTask<String, Void, Void> {
    private static Void a(String... strArr) {
        String str = strArr[0];
        while (str != null) {
            try {
                HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
                httpURLConnection.setInstanceFollowRedirects(true);
                str = httpURLConnection.getResponseCode() == 302 ? httpURLConnection.getHeaderField("Location") : null;
            } catch (IOException e) {
                t.b("Unable to check for redirect.", e);
            }
        }
        return null;
    }

    /* Return type fixed from 'java.lang.Object' to match base method */
    /* JADX DEBUG: Method arguments types fixed to match base method, original types: [java.lang.Object[]] */
    /* access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public final /* synthetic */ Void doInBackground(String[] strArr) {
        return a(strArr);
    }
}
