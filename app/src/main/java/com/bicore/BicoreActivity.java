package com.bicore;

import android.app.Activity;
import android.content.res.Configuration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import com.bicore.NativeFuntion;
import com.facebook.android.Facebook;
import com.feelingk.iap.IAPActivity;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class BicoreActivity extends IAPActivity implements NativeFuntion.EventListener {
    public static String AdmobID;
    private static Activity _activity = null;
    public static int iContentsHeight;
    public static int iContentsWidth;
    public static int iScreenHeight;
    public static int iScreenWidth;
    int ArmResult = 0;
    String DataFolder = null;
    String PackageName = null;
    String PackageSourcePath = null;
    String ResourceFolder = null;
    protected FacebookManager fbm;
    final String m2rFileName = "IGEngine.m2r";
    BicoreAudio mAudioSystem = null;
    Facebook mFacebook;
    private BicoreGraphic mGraphicSystem;

    @Override // com.bicore.NativeFuntion.EventListener
    public boolean GetCheckLooting() {
        return false;
    }

    @Override // com.bicore.NativeFuntion.EventListener
    public String GetPhoneNumber() {
        return "";
    }

    public static Activity GetActivity() {
        return _activity;
    }

    /* access modifiers changed from: protected */
    @Override // com.feelingk.iap.IAPActivity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.w("BicoreActivity", "*Frame - onCreate");
        getWindow().requestFeature(1);
        getWindow().setFlags(1024, 1024);
        getWindow().addFlags(128);
        iScreenWidth = getWindowManager().getDefaultDisplay().getWidth();
        iScreenHeight = getWindowManager().getDefaultDisplay().getHeight();
        NativeFuntion.nativeSetDeviceResolution(iScreenWidth, iScreenHeight);
        _activity = this;
    }

    public void onGameInit() {
        this.PackageName = getPackageName();
        this.PackageSourcePath = getPackageResourcePath();
        this.DataFolder = getApplicationInfo().dataDir;
        NativeFuntion.nativeSetResourceDirectory(this.PackageSourcePath);
        NativeFuntion.nativeSetDocumentDirectory(this.DataFolder);
        NativeFuntion.setListener(this);
        this.mAudioSystem = new BicoreAudio(this, this.DataFolder);
        if (iContentsWidth == -1) {
            iContentsWidth = iScreenWidth;
        }
        if (iContentsHeight == -1) {
            iContentsHeight = iScreenHeight;
        }
        NativeFuntion.nativeSetDeviceResolution(iScreenWidth, iScreenHeight);
        this.mGraphicSystem = new BicoreGraphic(this);
        setContentView(this.mGraphicSystem);
    }

    private void extractAllAssetFileFromApk(String sub) {
        try {
            byte[] buf = new byte[8096];
            String[] fileList = getAssets().list(sub);
            for (int i = 0; i < fileList.length; i++) {
                Log.i("APKExtracter", "Test  :" + sub + "/" + fileList[i]);
                if (!new File(String.valueOf(this.DataFolder) + "/" + fileList[i]).exists()) {
                    BufferedInputStream bis = new BufferedInputStream(getAssets().open(String.valueOf(sub) + "/" + fileList[i]));
                    FileOutputStream fw = new FileOutputStream(String.valueOf(this.DataFolder) + "/" + fileList[i]);
                    Log.d("APKExtracter", "File  :" + fileList[i]);
                    while (bis.available() > 0) {
                        bis.read(buf);
                        fw.write(buf);
                    }
                    fw.close();
                    bis.close();
                    Log.d("APKExtracter", "Complete");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void extractAssetFileFromApk(String filename) {
        try {
            File fm24 = new File(String.valueOf(this.DataFolder) + "/" + filename);
            ZipFile file = new ZipFile(this.PackageSourcePath);
            ZipEntry entry = file.getEntry("assets/" + filename);
            if (fm24.exists()) {
                Log.w("BicoreActivity", "File M2R File size :" + fm24.length());
                Log.w("BicoreActivity", "Zip M2R File size :" + entry.getSize());
                if (fm24.length() == entry.getSize()) {
                    Log.w("BicoreActivity", "File M2R File time :" + fm24.lastModified());
                    Log.w("BicoreActivity", "Zip M2R File time :" + entry.getTime());
                    return;
                }
            }
            BufferedInputStream input2 = new BufferedInputStream(file.getInputStream(entry), 8096);
            System.out.println("Got the input stream...");
            fm24.createNewFile();
            OutputStream output2 = new FileOutputStream(fm24);
            System.out.println("Got the output stream...");
            byte[] buf = new byte[8096];
            for (int read = input2.read(buf, 0, 8096); read > 0; read = input2.read(buf, 0, 8096)) {
                output2.write(buf, 0, read);
            }
            output2.close();
            input2.close();
            System.out.println("Check File result");
            File fm242 = new File(filename);
            Log.w("BicoreActivity", "File M2R File size :" + fm242.length());
            Log.w("BicoreActivity", "Zip M2R File size :" + entry.getSize());
            Log.w("BicoreActivity", "File M2R File time :" + fm242.lastModified());
            Log.w("BicoreActivity", "Zip M2R File time :" + entry.getTime());
            fm242.setLastModified(entry.getTime());
            Log.w("BicoreActivity", "File M2R File time set to:" + fm242.lastModified());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: protected */
    public void onRestart() {
        Log.w("BicoreActivity", "*Frame - onRestart");
        super.onRestart();
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        Log.w("BicoreActivity", "*Frame - onStart");
        super.onStart();
    }

    /* access modifiers changed from: protected */
    @Override // com.feelingk.iap.IAPActivity
    public void onPause() {
        Log.w("BicoreActivity", "*Frame - onPause");
        super.onPause();
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Log.w("BicoreActivity", "*Frame - onWindowFocusChanged" + hasFocus);
        if (hasFocus) {
            if (this.mAudioSystem != null) {
                this.mAudioSystem.resume();
            }
        } else if (this.mAudioSystem != null) {
            this.mAudioSystem.pause();
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.feelingk.iap.IAPActivity
    public void onResume() {
        super.onResume();
        Log.w("BicoreActivity", "*Frame - onResume");
        if (this.mGraphicSystem != null) {
            this.mGraphicSystem.onResume();
        }
        NativeFuntion.nativeResumeClet();
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
        Log.w("BicoreActivity", "*Frame - onStop");
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        Log.w("BicoreActivity", "*Frame - onDestroy");
        GetActivity().runOnUiThread(new Runnable() {
            /* class com.bicore.BicoreActivity.AnonymousClass1 */

            public void run() {
                NativeFuntion.nativeDone();
            }
        });
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.w("BicoreActivity", "*Frame - onConfigurationChanged");
        switch (newConfig.orientation) {
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.w("BicoreActivity", "*Frame - onKeyDown " + event.getKeyCode());
        if (keyCode == 82) {
            Log.w("BicoreActivity", "KeyEvent KEYCODE_MENU");
            return true;
        } else if (keyCode != 4) {
            return super.onKeyDown(keyCode, event);
        } else {
            Log.w("BicoreActivity", "KeyEvent KEYCODE_BACK");
            GetActivity().runOnUiThread(new Runnable() {
                /* class com.bicore.BicoreActivity.AnonymousClass2 */

                public void run() {
                    NativeFuntion.nativeHandleEvent(2, -22, 0, 0, 0);
                }
            });
            return true;
        }
    }

    @Override // com.bicore.NativeFuntion.EventListener
    public void OnMessage(String text) {
        Log.w("BicoreActivity", "*OnMessage : " + text);
    }

    @Override // com.bicore.NativeFuntion.EventListener
    public void OnURLOpen(String url) {
        Log.w("BicoreActivity", "*OnURLOpen : " + url);
    }

    @Override // com.bicore.NativeFuntion.EventListener
    public String GetDeviceID() {
        return ((TelephonyManager) getApplicationContext().getSystemService("phone")).getDeviceId();
    }

    @Override // com.bicore.NativeFuntion.EventListener
    public String GetModelType() {
        return Build.MODEL;
    }

    @Override // com.bicore.NativeFuntion.EventListener
    public boolean IsEnableDataConnect() {
        if (((TelephonyManager) getSystemService("phone")).getDataState() == 2) {
            return true;
        }
        return false;
    }

    @Override // com.bicore.NativeFuntion.EventListener
    public boolean IsEnableWifi() {
        if (((WifiManager) getSystemService("wifi")).getWifiState() == 3) {
            return true;
        }
        return false;
    }

    @Override // com.bicore.NativeFuntion.EventListener
    public void OnExitApplication() {
        Log.w("BicoreActivity", "*OnExitApplication ");
        runOnUiThread(new Runnable() {
            /* class com.bicore.BicoreActivity.AnonymousClass3 */

            public void run() {
                BicoreActivity.this.finish();
            }
        });
    }

    @Override // com.bicore.NativeFuntion.EventListener
    public void CallVibration(int miliseconds) {
        ((Vibrator) getSystemService("vibrator")).vibrate((long) miliseconds);
    }

    @Override // com.bicore.NativeFuntion.EventListener
    public boolean GetAirplaneModeState() {
        try {
            if (Settings.System.getInt(getContentResolver(), "airplane_mode_on") > 0) {
                return true;
            }
            return false;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override // com.bicore.NativeFuntion.EventListener
    public void OnRequestPayment(String paycode) {
        Log.e("BicoreActivity", "*OnRequestPayment - Not avilable. make MarketClass inheritive.");
    }

    @Override // com.bicore.NativeFuntion.EventListener
    public String GetCountryCode() {
        return Locale.getDefault().getCountry();
    }

    @Override // com.bicore.NativeFuntion.EventListener
    public String GetLanguageCode() {
        return Locale.getDefault().getLanguage();
    }
}
