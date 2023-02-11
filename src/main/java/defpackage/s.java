package defpackage;

import android.app.Activity;
import android.webkit.WebView;
import android.widget.VideoView;
import com.google.ads.AdActivity;
import java.util.HashMap;

/* renamed from: s  reason: default package */
public final class s implements i {
    @Override // defpackage.i
    public final void a(d dVar, HashMap<String, String> hashMap, WebView webView) {
        String str = hashMap.get("action");
        if (webView instanceof g) {
            AdActivity b = ((g) webView).b();
            if (b == null) {
                t.a("Could not get adActivity to create the video.");
            } else if (str.equals("load")) {
                String str2 = hashMap.get("url");
                Activity c = dVar.c();
                if (c == null) {
                    t.e("activity was null while loading a video.");
                    return;
                }
                VideoView videoView = new VideoView(c.getApplicationContext());
                videoView.setVideoPath(str2);
                b.showVideo(videoView);
            } else {
                VideoView videoView2 = b.getVideoView();
                if (videoView2 == null) {
                    t.e("Could not get the VideoView for a video GMSG.");
                } else if (str.equals("play")) {
                    videoView2.setVisibility(0);
                    videoView2.start();
                } else if (str.equals("pause")) {
                    videoView2.pause();
                } else if (str.equals("stop")) {
                    videoView2.stopPlayback();
                } else if (str.equals("remove")) {
                    videoView2.setVisibility(8);
                } else if (str.equals("replay")) {
                    videoView2.setVisibility(0);
                    videoView2.seekTo(0);
                    videoView2.start();
                } else if (str.equals("currentTime")) {
                    String str3 = hashMap.get("time");
                    if (str3 == null) {
                        t.e("No \"time\" parameter!");
                    } else {
                        videoView2.seekTo((int) (Float.valueOf(str3).floatValue() * 1000.0f));
                    }
                } else if (!str.equals("position")) {
                    t.e("Unknown movie action: " + str);
                }
            }
        } else {
            t.a("Could not get adWebView to create the video.");
        }
    }
}
