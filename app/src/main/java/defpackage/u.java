package defpackage;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.location.Location;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.webkit.WebView;
import com.google.ads.AdActivity;
import com.google.ads.AdRequest;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/* renamed from: u  reason: default package */
public final class u {
    private static Boolean a = null;
    private static String b = null;
    private static String c;
    private static AudioManager d;
    private static DisplayMetrics e;
    private static boolean f = true;
    private static boolean g = false;
    private static String h = null;

    /* renamed from: u$a */
    public enum a {
        INVALID,
        SPEAKER,
        HEADPHONES,
        VIBRATE,
        EMULATOR,
        OTHER
    }

    /* access modifiers changed from: private */
    /* renamed from: u$b */
    public static class b extends BroadcastReceiver {
        /* synthetic */ b() {
            this((byte) 0);
        }

        private b(byte b) {
        }

        public final void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.intent.action.USER_PRESENT")) {
                u.a(true);
            } else if (intent.getAction().equals("android.intent.action.SCREEN_OFF")) {
                u.a(false);
            }
        }
    }

    private u() {
    }

    public static DisplayMetrics a(Activity activity) {
        if (e == null) {
            e = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(e);
        }
        return e;
    }

    public static String a(Context context) {
        if (b == null) {
            String string = Settings.Secure.getString(context.getContentResolver(), "android_id");
            String a2 = (string == null || a()) ? a("emulator") : a(string);
            if (a2 == null) {
                return null;
            }
            b = a2.toUpperCase();
        }
        return b;
    }

    public static String a(Location location) {
        if (location == null) {
            return null;
        }
        return "e1+" + b(String.format("role: 6 producer: 24 historical_role: 1 historical_producer: 12 timestamp: %d latlng < latitude_e7: %d longitude_e7: %d> radius: %d", Long.valueOf(location.getTime() * 1000), Long.valueOf((long) (location.getLatitude() * 1.0E7d)), Long.valueOf((long) (location.getLongitude() * 1.0E7d)), Long.valueOf((long) (location.getAccuracy() * 1000.0f))));
    }

    public static String a(WebView webView) {
        if (h == null) {
            String userAgentString = webView.getSettings().getUserAgentString();
            if (userAgentString == null || userAgentString.equals("Java0")) {
                userAgentString = System.getProperty("java.vm.name", "Dalvik") + "/" + System.getProperty("java.vm.version", "1.1.0") + " (" + System.getProperty("os.name", "Linux") + "; U; Android " + Build.VERSION.RELEASE + "; " + Build.MODEL + " Build/" + Build.ID + ")";
            }
            h = userAgentString + " (Mobile; " + "afma-sdk-a-v" + AdRequest.VERSION + ")";
        }
        return h;
    }

    private static String a(String str) {
        if (str == null || str.length() <= 0) {
            return null;
        }
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(str.getBytes(), 0, str.length());
            return String.format("%032X", new BigInteger(1, instance.digest()));
        } catch (NoSuchAlgorithmException e2) {
            return str.substring(0, 32);
        }
    }

    public static HashMap<String, String> a(Uri uri) {
        if (uri == null) {
            return null;
        }
        HashMap<String, String> hashMap = new HashMap<>();
        String encodedQuery = uri.getEncodedQuery();
        if (encodedQuery == null) {
            return hashMap;
        }
        String[] split = encodedQuery.split("&");
        for (String str : split) {
            int indexOf = str.indexOf(61);
            if (indexOf == -1) {
                return null;
            }
            hashMap.put(URLDecoder.decode(str.substring(0, indexOf)), URLDecoder.decode(str.substring(indexOf + 1)));
        }
        return hashMap;
    }

    public static void a(boolean z) {
        f = z;
    }

    public static boolean a() {
        return "unknown".equals(Build.BOARD) && "generic".equals(Build.DEVICE) && "generic".equals(Build.BRAND);
    }

    private static String b(String str) {
        try {
            Cipher instance = Cipher.getInstance("AES/CBC/PKCS5Padding");
            instance.init(1, new SecretKeySpec(new byte[]{10, 55, -112, -47, -6, 7, 11, 75, -7, -121, 121, 69, 80, -61, 15, 5}, "AES"));
            byte[] iv = instance.getIV();
            byte[] doFinal = instance.doFinal(str.getBytes());
            byte[] bArr = new byte[(iv.length + doFinal.length)];
            System.arraycopy(iv, 0, bArr, 0, iv.length);
            System.arraycopy(doFinal, 0, bArr, iv.length, doFinal.length);
            return v.a(bArr);
        } catch (GeneralSecurityException e2) {
            return null;
        }
    }

    public static void b(WebView webView) {
        webView.getSettings().setUserAgentString(a(webView));
    }

    public static boolean b() {
        return f;
    }

    public static boolean b(Context context) {
        boolean z;
        if (a != null) {
            return a.booleanValue();
        }
        ResolveInfo resolveActivity = context.getPackageManager().resolveActivity(new Intent(context, AdActivity.class), 65536);
        boolean z2 = true;
        if (resolveActivity == null || resolveActivity.activityInfo == null) {
            t.b("Could not find com.google.ads.AdActivity, please make sure it is registered in AndroidManifest.xml.");
            z = false;
        } else {
            if ((resolveActivity.activityInfo.configChanges & 16) == 0) {
                t.b("The android:configChanges value of the com.google.ads.internal.AdActivity must include keyboard.");
                z2 = false;
            }
            if ((resolveActivity.activityInfo.configChanges & 32) == 0) {
                t.b("The android:configChanges value of the com.google.ads.internal.AdMobActivity must include keyboardHidden.");
                z2 = false;
            }
            if ((resolveActivity.activityInfo.configChanges & 128) == 0) {
                t.b("The android:configChanges value of the com.google.ads.internal.AdActivity must include orientation.");
                z = false;
            } else {
                z = z2;
            }
        }
        a = Boolean.valueOf(z);
        return z;
    }

    public static String c(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo == null) {
            return null;
        }
        switch (activeNetworkInfo.getType()) {
            case 0:
                return "ed";
            case 1:
                return "wi";
            default:
                return "unknown";
        }
    }

    public static String d(Context context) {
        if (c == null) {
            StringBuilder sb = new StringBuilder();
            PackageManager packageManager = context.getPackageManager();
            List<ResolveInfo> queryIntentActivities = packageManager.queryIntentActivities(new Intent("android.intent.action.VIEW", Uri.parse("geo:0,0?q=donuts")), 65536);
            if (queryIntentActivities == null || queryIntentActivities.size() == 0) {
                sb.append(AdActivity.TYPE_PARAM);
            }
            List<ResolveInfo> queryIntentActivities2 = packageManager.queryIntentActivities(new Intent("android.intent.action.VIEW", Uri.parse("market://search?q=pname:com.admob")), 65536);
            if (queryIntentActivities2 == null || queryIntentActivities2.size() == 0) {
                if (sb.length() > 0) {
                    sb.append(",");
                }
                sb.append("a");
            }
            List<ResolveInfo> queryIntentActivities3 = packageManager.queryIntentActivities(new Intent("android.intent.action.VIEW", Uri.parse("tel://6509313940")), 65536);
            if (queryIntentActivities3 == null || queryIntentActivities3.size() == 0) {
                if (sb.length() > 0) {
                    sb.append(",");
                }
                sb.append("t");
            }
            c = sb.toString();
        }
        return c;
    }

    public static a e(Context context) {
        if (d == null) {
            d = (AudioManager) context.getSystemService("audio");
        }
        a aVar = a.OTHER;
        int mode = d.getMode();
        if (a()) {
            return a.EMULATOR;
        }
        if (d.isMusicActive() || d.isSpeakerphoneOn() || mode == 2 || mode == 1) {
            return a.VIBRATE;
        }
        int ringerMode = d.getRingerMode();
        return (ringerMode == 0 || ringerMode == 1) ? a.VIBRATE : a.SPEAKER;
    }

    public static String f(Context context) {
        return ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getOrientation() == 1 ? "l" : "p";
    }

    public static void g(Context context) {
        if (!g) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.USER_PRESENT");
            intentFilter.addAction("android.intent.action.SCREEN_OFF");
            context.registerReceiver(new b(), intentFilter);
            g = true;
        }
    }
}
