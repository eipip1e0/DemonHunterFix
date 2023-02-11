package defpackage;

import android.os.AsyncTask;
import com.google.ads.AdRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.StringTokenizer;

/* renamed from: b  reason: default package */
public final class b extends AsyncTask<String, Void, Void> {
    private c a;
    private d b;
    private String c;

    b(c cVar, d dVar, String str) {
        this.a = cVar;
        this.b = dVar;
        this.c = str;
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public Void doInBackground(String... strArr) {
        String str = strArr[0];
        while (true) {
            try {
                HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
                httpURLConnection.setRequestProperty("User-Agent", this.c);
                httpURLConnection.setInstanceFollowRedirects(false);
                httpURLConnection.connect();
                int responseCode = httpURLConnection.getResponseCode();
                if (responseCode == 302) {
                    str = httpURLConnection.getHeaderField("Location");
                    if (str == null) {
                        t.c("Could not get redirect location from a 302 redirect.");
                        this.a.a(AdRequest.ErrorCode.INTERNAL_ERROR);
                        return null;
                    }
                    b(httpURLConnection);
                    c(httpURLConnection);
                    a(httpURLConnection);
                    d(httpURLConnection);
                } else if (responseCode == 200) {
                    b(httpURLConnection);
                    c(httpURLConnection);
                    a(httpURLConnection);
                    d(httpURLConnection);
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    while (true) {
                        String readLine = bufferedReader.readLine();
                        if (readLine == null) {
                            break;
                        }
                        sb.append(readLine);
                        sb.append("\n");
                    }
                    String sb2 = sb.toString();
                    t.a("Response content is: " + sb2);
                    if (sb2 == null || sb2.trim().length() <= 0) {
                        t.a("Response message is null or zero length: " + sb2);
                        this.a.a(AdRequest.ErrorCode.NO_FILL);
                        return null;
                    }
                    this.a.a(sb2, str);
                    return null;
                } else if (responseCode == 400) {
                    t.c("Bad request");
                    this.a.a(AdRequest.ErrorCode.INVALID_REQUEST);
                    return null;
                } else {
                    t.c("Invalid response code: " + responseCode);
                    this.a.a(AdRequest.ErrorCode.INTERNAL_ERROR);
                    return null;
                }
            } catch (MalformedURLException e) {
                t.a("Received malformed ad url from javascript.", e);
                this.a.a(AdRequest.ErrorCode.INTERNAL_ERROR);
                return null;
            } catch (IOException e2) {
                t.b("IOException connecting to ad url.", e2);
                this.a.a(AdRequest.ErrorCode.NETWORK_ERROR);
                return null;
            }
        }
    }

    private static void a(HttpURLConnection httpURLConnection) {
        String headerField = httpURLConnection.getHeaderField("X-Afma-Timeout");
        if (headerField != null) {
            c.a((long) (Float.parseFloat(headerField) * 1000.0f));
        }
    }

    private void b(HttpURLConnection httpURLConnection) {
        String headerField = httpURLConnection.getHeaderField("X-Afma-Tracking-Urls");
        if (headerField != null) {
            StringTokenizer stringTokenizer = new StringTokenizer(headerField);
            while (stringTokenizer.hasMoreTokens()) {
                this.b.a(stringTokenizer.nextToken());
            }
        }
    }

    private void c(HttpURLConnection httpURLConnection) {
        String headerField = httpURLConnection.getHeaderField("X-Afma-Refresh-Rate");
        if (headerField != null) {
            this.b.a(Float.parseFloat(headerField));
            if (!this.b.m()) {
                this.b.b();
            }
        }
    }

    private void d(HttpURLConnection httpURLConnection) {
        String headerField = httpURLConnection.getHeaderField("X-Afma-Orientation");
        if (headerField == null) {
            return;
        }
        if (headerField.equals("portrait")) {
            this.b.a(1);
        } else if (headerField.equals("landscape")) {
            this.b.a(0);
        }
    }
}
