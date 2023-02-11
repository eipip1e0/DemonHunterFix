package com.google.ads;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.SystemClock;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.VideoView;

public class AdActivity extends Activity implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, View.OnClickListener {
    public static final String BASE_URL_PARAM = "baseurl";
    public static final String HTML_PARAM = "html";
    public static final String INTENT_ACTION_PARAM = "i";
    public static final String ORIENTATION_PARAM = "o";
    public static final String TYPE_PARAM = "m";
    public static final String URL_PARAM = "u";
    private static final Object a = new Object();
    private static AdActivity b = null;
    private static d c = null;
    private static AdActivity d = null;
    private g e;
    private long f;
    private RelativeLayout g;
    private boolean h;
    private VideoView i;

    private void a(g gVar, boolean z, int i2) {
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        if (gVar.getParent() != null) {
            a("Interstitial created with an AdWebView that has a parent.");
        } else if (gVar.b() != null) {
            a("Interstitial created with an AdWebView that is already in use by another AdActivity.");
        } else {
            setRequestedOrientation(i2);
            gVar.a(this);
            ImageButton imageButton = new ImageButton(getApplicationContext());
            imageButton.setImageResource(17301527);
            imageButton.setBackgroundDrawable(null);
            int applyDimension = (int) TypedValue.applyDimension(1, 1.0f, getResources().getDisplayMetrics());
            imageButton.setPadding(applyDimension, applyDimension, 0, 0);
            imageButton.setOnClickListener(this);
            this.g.addView(gVar, new ViewGroup.LayoutParams(-1, -1));
            this.g.addView(imageButton);
            setContentView(this.g);
            if (z) {
                a.a(gVar);
            }
        }
    }

    private void a(String str) {
        t.b(str);
        finish();
    }

    public static void launchAdActivity(d dVar, e eVar) {
    }

    public VideoView getVideoView() {
        return this.i;
    }

    public void onClick(View view) {
        finish();
    }

    public void onCompletion(MediaPlayer mediaPlayer) {
        t.d("Video finished playing.");
        this.e.loadUrl("javascript:videoController.showReplayAndSplash_()");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:101:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:102:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x0111, code lost:
        r2.b();
        r11.e.setWebViewClient(r2);
        r1 = r4.get(com.google.ads.AdActivity.URL_PARAM);
        r2 = r4.get(com.google.ads.AdActivity.BASE_URL_PARAM);
        r3 = r4.get(com.google.ads.AdActivity.HTML_PARAM);
        r0 = r4.get(com.google.ads.AdActivity.ORIENTATION_PARAM);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x013b, code lost:
        if (r1 == null) goto L_0x0160;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x013d, code lost:
        r11.e.loadUrl(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x0142, code lost:
        r1 = 4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x0143, code lost:
        if (r0 == null) goto L_0x014e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x014b, code lost:
        if (r0.equals("p") == false) goto L_0x0173;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x014d, code lost:
        r1 = 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x014e, code lost:
        a(r11.e, false, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x0160, code lost:
        if (r3 == null) goto L_0x016c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x0162, code lost:
        r11.e.loadDataWithBaseURL(r2, r3, "text/html", "utf-8", null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x016c, code lost:
        a("Could not get the URL or HTML parameter to show a web app.");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:0x0179, code lost:
        if (r0.equals("l") == false) goto L_0x014e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x017b, code lost:
        r1 = 0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onCreate(android.os.Bundle r12) {
        /*
        // Method dump skipped, instructions count: 459
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.ads.AdActivity.onCreate(android.os.Bundle):void");
    }

    public void onDestroy() {
        if (this.g != null) {
            this.g.removeAllViews();
        }
        if (this.e != null) {
            a.b(this.e);
            this.e.a(null);
        }
        if (isFinishing()) {
            if (this.i != null) {
                this.i.stopPlayback();
                this.i = null;
            }
            synchronized (a) {
                if (this == d) {
                    if (c != null) {
                        c.o();
                        c = null;
                    } else {
                        t.e("currentAdManager is null while trying to destroy AdActivity.");
                    }
                    d = null;
                }
                if (this == b) {
                    b = null;
                }
            }
        }
        t.a("AdActivity is closing.");
        super.onDestroy();
    }

    public void onPrepared(MediaPlayer mediaPlayer) {
        t.d("Video is ready to play.");
        this.e.loadUrl("javascript:videoController.hideSplashAndPlayVideo_()");
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (this.h && hasFocus && SystemClock.elapsedRealtime() - this.f > 250) {
            t.d("Launcher AdActivity got focus and is closing.");
            finish();
        }
    }

    public void showVideo(VideoView videoView) {
        this.i = videoView;
        if (this.e == null) {
            a("Couldn't get adWebView to show the video.");
            return;
        }
        this.e.setBackgroundColor(0);
        videoView.setOnCompletionListener(this);
        videoView.setOnPreparedListener(this);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-1, -1);
        LinearLayout linearLayout = new LinearLayout(getApplicationContext());
        linearLayout.setGravity(17);
        linearLayout.addView(videoView, layoutParams);
        this.g.addView(linearLayout, 0, layoutParams);
    }
}
