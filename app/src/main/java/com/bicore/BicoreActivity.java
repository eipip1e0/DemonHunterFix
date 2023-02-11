package com.bicore;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.bicore.NativeFuntion;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class BicoreActivity extends Activity implements NativeFuntion.EventListener {
    private static final String TAG = "BicoreActivity";

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
    final String m2rFileName = "IGEngine.m2r";
    BicoreAudio mAudioSystem = null;
    private BicoreGraphic mGraphicSystem;

    /**
     * rooted device check
     */
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
        initFileDir();
        onFirstOpenGame();
        Log.w(TAG, "*Frame - onCreate");
        getWindow().requestFeature(1);
        getWindow().setFlags(1024, 1024);
        getWindow().addFlags(128);
        iScreenWidth = getWindowManager().getDefaultDisplay().getWidth();
        iScreenHeight = getWindowManager().getDefaultDisplay().getHeight();
        NativeFuntion.nativeSetDeviceResolution(iScreenWidth, iScreenHeight);
        _activity = this;
    }

    private void initFileDir() {
        this.PackageName = getPackageName();
        this.PackageSourcePath = getPackageResourcePath();
        this.DataFolder = getExternalFilesDir("SaveData").getAbsolutePath();
    }

    private void onFirstOpenGame() {
        SharedPreferences shared = getSharedPreferences("first_start_flag", MODE_PRIVATE);
        boolean isFirstStart = shared.getBoolean("isFirstStart", true);
        SharedPreferences.Editor editor = shared.edit();
        if(isFirstStart) {
            extractAssetFileFromApk("Config.dat");
            extractAssetFileFromApk("Save0.dat");
            extractAssetFileFromApk("Save1.dat");
            extractAssetFileFromApk("Save2.dat");
            editor.putBoolean("isFirstStart", false);
            editor.apply();
        }
    }

    public void onGameInit() {
        Toast.makeText(this, "Mod by 百度贴吧 @s戀上漃謨s (eipip1e0@163.com)", Toast.LENGTH_LONG).show();
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
                Log.w(TAG, "Save data already exists");
                return;
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
            Log.w(TAG, "File M2R File size :" + fm242.length());
            Log.w(TAG, "Zip M2R File size :" + entry.getSize());
            Log.w(TAG, "File M2R File time :" + fm242.lastModified());
            Log.w(TAG, "Zip M2R File time :" + entry.getTime());
            fm242.setLastModified(entry.getTime());
            Log.w(TAG, "File M2R File time set to:" + fm242.lastModified());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: protected */
    public void onRestart() {
        Log.w(TAG, "*Frame - onRestart");
        super.onRestart();
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        Log.w(TAG, "*Frame - onStart");
        super.onStart();
    }

    /* access modifiers changed from: protected */
    @Override // com.feelingk.iap.IAPActivity
    public void onPause() {
        Log.w(TAG, "*Frame - onPause");
        super.onPause();
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Log.w(TAG, "*Frame - onWindowFocusChanged" + hasFocus);
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
        Log.w(TAG, "*Frame - onResume");
        if (this.mGraphicSystem != null) {
            this.mGraphicSystem.onResume();
        }
        NativeFuntion.nativeResumeClet();
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
        Log.w(TAG, "*Frame - onStop");
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        Log.w(TAG, "*Frame - onDestroy");
        GetActivity().runOnUiThread(new Runnable() {
            /* class com.bicore.BicoreActivity.AnonymousClass1 */

            public void run() {
                NativeFuntion.nativeDone();
            }
        });
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.w(TAG, "*Frame - onConfigurationChanged");
        switch (newConfig.orientation) {
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.w(TAG, "*Frame - onKeyDown " + event.getKeyCode());
        if (keyCode == 82) {
            Log.w(TAG, "KeyEvent KEYCODE_MENU");
            return true;
        } else if (keyCode != 4) {
            return super.onKeyDown(keyCode, event);
        } else {
            Log.w(TAG, "KeyEvent KEYCODE_BACK");
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
        Log.w(TAG, "*OnMessage : " + text);
    }

    @Override // com.bicore.NativeFuntion.EventListener
    public void OnURLOpen(String url) {
        Log.w(TAG, "*OnURLOpen : " + url);
    }

    @Override // com.bicore.NativeFuntion.EventListener
    public String GetDeviceID() {
        Log.d(TAG, "GetDeviceID: ");
        return "866150004500431";
    }

    @Override // com.bicore.NativeFuntion.EventListener
    public String GetModelType() {
        Log.d(TAG, "GetModelType: " + Build.MODEL);
        return Build.MODEL;
    }

    @Override // com.bicore.NativeFuntion.EventListener
    public boolean IsEnableDataConnect() {
        Log.d(TAG, "IsEnableDataConnect: ");
        if (((TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE)).getDataState() == TelephonyManager.DATA_CONNECTED) {
            return true;
        }
        return false;
    }

    @Override // com.bicore.NativeFuntion.EventListener
    public boolean IsEnableWifi() {
        Log.d(TAG, "IsEnableWifi: ");
        if (((WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE)).getWifiState() == 3) {
            return true;
        }
        return false;
    }

    @Override // com.bicore.NativeFuntion.EventListener
    public void OnExitApplication() {
        Log.w(TAG, "*OnExitApplication ");
        runOnUiThread(new Runnable() {
            /* class com.bicore.BicoreActivity.AnonymousClass3 */

            public void run() {
                BicoreActivity.this.finish();
            }
        });
    }

    @Override // com.bicore.NativeFuntion.EventListener
    public void CallVibration(int miliseconds) {
        ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate((long) miliseconds);
    }

    @Override // com.bicore.NativeFuntion.EventListener
    public boolean GetAirplaneModeState() {
        Log.d(TAG, "GetAirplaneModeState: ");
        try {
            return Settings.System.getInt(getContentResolver(), "airplane_mode_on") > 0;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override // com.bicore.NativeFuntion.EventListener
    public void OnRequestPayment(String paycode) {
        Log.d(TAG, "OnRequestPayment: ");
        Log.e(TAG, "*OnRequestPayment - Not avilable. make MarketClass inheritive.");
    }

    @Override // com.bicore.NativeFuntion.EventListener
    public String GetCountryCode() {
        Log.d(TAG, "GetCountryCode: ");
        return Locale.getDefault().getCountry();
    }

    @Override // com.bicore.NativeFuntion.EventListener
    public String GetLanguageCode() {
        Log.d(TAG, "GetLanguageCode: ");
        return Locale.getDefault().getLanguage();
    }
}
