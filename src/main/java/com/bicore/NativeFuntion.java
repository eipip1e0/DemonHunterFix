package com.bicore;

import android.media.AudioTrack;
import android.widget.EditText;
import android.widget.TextView;
import com.bicore.Font;

public class NativeFuntion {
    private static AudioEventListener audioListener;
    private static BitmapCraterEventListener bitmapcreaterListener;
    private static FacebookEventListner facebookListnener;
    private static FrameEventListener frameListener;
    private static RenderEventListener renderListener;
    private static EventListener systemListener;

    public interface AudioEventListener {
        int CreateMP3AudioClip(String str, int i, int i2);

        int CreateWAVAudioClip(String str, int i, int i2);

        int GetMP3AudioClipCurrentPosition(int i);

        int PlayMP3Audio(int i, boolean z);

        int PlayWAVAudio(int i);

        void SetMP3Volume(int i, int i2);

        void SetWAVVolume(int i, int i2);

        void StopMP3Audio(int i);

        void StopWAVAudio(int i);

        void UnloadMP3AudioClip(int i);

        void UnloadWAVAudioClip(int i);
    }

    public interface BitmapCraterEventListener {
        int CreateBitmap(int i, int i2, byte[] bArr);

        int CreateBitmap(int i, String str, int i2, int i3);

        void DestoryBitmap(int i);
    }

    public interface EventListener {
        void CallVibration(int i);

        boolean GetAirplaneModeState();

        boolean GetCheckLooting();

        String GetCountryCode();

        String GetDeviceID();

        String GetLanguageCode();

        String GetModelType();

        String GetPhoneNumber();

        boolean IsEnableDataConnect();

        boolean IsEnableWifi();

        void OnExitApplication();

        void OnMessage(String str);

        void OnRequestPayment(String str);

        void OnURLOpen(String str);
    }

    public interface FacebookEventListner {
        boolean isVaild();

        void onFacebookInit(String str, String str2, String str3);

        void onLogin();

        void onLogout();

        void onPublish(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, String str13);

        void onQuery(String str);
    }

    public interface FrameEventListener {
        void DeleteText(EditText editText, int i, int i2);

        void EnableControl(EditText editText, boolean z);

        int GetAdState();

        EditText GetEditBoxControl();

        int GetMaxTextSize(EditText editText);

        String GetText(EditText editText);

        int GetTextSize(EditText editText);

        void HideAdmob();

        void HideBanner();

        void InsertText(EditText editText, String str, int i, int i2);

        void SetInputType(boolean z, boolean z2, boolean z3);

        void SetMaxTextSize(EditText editText, int i);

        void SetPosition(EditText editText, int i, int i2, int i3, int i4);

        void ShowAdmob(int i, int i2, int i3);

        void ShowBanner(int i, int i2, int i3, int i4, String str);
    }

    public interface RenderEventListener {
        void SetTimer(int i);
    }

    public static native int nativeCreateEmptyTexture(int i, int i2, int i3, int i4, int i5);

    public static native void nativeDeleteTexture(int i);

    public static native void nativeDone();

    public static native void nativeDrawString(int i, int i2, int i3, int i4, int i5, float f, float f2, float f3, float f4, int i6, int i7);

    public static native void nativeGetAudioDataStream(AudioTrack audioTrack);

    public static native void nativeHandleEvent(int i, int i2, int i3, int i4, int i5);

    public static native void nativeIGMRun();

    public static native void nativeInit();

    public static native void nativeInvaildateAllTexture();

    public static native void nativePauseClet();

    public static native void nativePuchaseCancel();

    public static native void nativePuchaseComplete();

    public static native void nativePuchaseFail(int i);

    public static native void nativeRender();

    public static native void nativeResize(int i, int i2);

    public static native void nativeResumeClet();

    public static native void nativeSetDeviceResolution(int i, int i2);

    public static native void nativeSetDocumentDirectory(String str);

    public static native void nativeSetResourceDirectory(String str);

    public static native void nativeTextureSubImage2D(int i, byte[] bArr, int i2, int i3, int i4, int i5);

    public static void setListener(EventListener l) {
        systemListener = l;
    }

    public static void setFrameEventListener(FrameEventListener l) {
        frameListener = l;
    }

    public static void setAudioEventListener(AudioEventListener l) {
        audioListener = l;
    }

    public static void setRenderEventListener(RenderEventListener l) {
        renderListener = l;
    }

    public static void setFacebookEventListener(FacebookEventListner l) {
        facebookListnener = l;
    }

    public static void setBitmapCreaterEventListener(BitmapCraterEventListener l) {
        bitmapcreaterListener = l;
    }

    public static void OnMessage(String text) {
        if (systemListener != null) {
            systemListener.OnMessage(text);
        }
    }

    public static void OnURLOpen(String url) {
        if (systemListener != null) {
            systemListener.OnURLOpen(url);
        }
    }

    public static void OnRequestPayment(String paycode) {
        if (systemListener != null) {
            systemListener.OnRequestPayment(paycode);
        }
    }

    public static String GetDeviceID() {
        if (systemListener != null) {
            return systemListener.GetDeviceID();
        }
        return null;
    }

    public static String GetModelType() {
        if (systemListener != null) {
            return systemListener.GetModelType();
        }
        return null;
    }

    public static String GetPhoneNumber() {
        if (systemListener != null) {
            return systemListener.GetPhoneNumber();
        }
        return null;
    }

    public static String GetCountryCode() {
        if (systemListener != null) {
            return systemListener.GetCountryCode();
        }
        return null;
    }

    public static String GetLanguageCode() {
        if (systemListener != null) {
            return systemListener.GetLanguageCode();
        }
        return null;
    }

    public static boolean GetAirplaneModeState() {
        if (systemListener != null) {
            return systemListener.GetAirplaneModeState();
        }
        return false;
    }

    public static boolean GetCheckLooting() {
        if (systemListener != null) {
            return systemListener.GetCheckLooting();
        }
        return false;
    }

    public static void OnExitApplication() {
        if (systemListener != null) {
            systemListener.OnExitApplication();
        }
    }

    public static void CallVibration(int miliseconds) {
        if (systemListener != null) {
            systemListener.CallVibration(miliseconds);
        }
    }

    public static boolean IsEnableDataConnect() {
        if (systemListener != null) {
            return systemListener.IsEnableDataConnect();
        }
        return false;
    }

    public static boolean IsEnableWifi() {
        if (systemListener != null) {
            return systemListener.IsEnableWifi();
        }
        return false;
    }

    public static EditText GetEditBoxControl() {
        if (frameListener != null) {
            return frameListener.GetEditBoxControl();
        }
        return null;
    }

    public static TextView GetTextViewControl() {
        return null;
    }

    public static void EnableControl(EditText v, boolean bEnable) {
        if (frameListener != null) {
            frameListener.EnableControl(v, bEnable);
        }
    }

    public static void SetPosition(EditText v, int l, int t, int w, int h) {
        if (frameListener != null) {
            frameListener.SetPosition(v, l, t, w, h);
        }
    }

    public static void InsertText(EditText v, String s, int start, int length) {
        if (frameListener != null) {
            frameListener.InsertText(v, s, start, length);
        }
    }

    public static void DeleteText(EditText v, int start, int length) {
        if (frameListener != null) {
            frameListener.DeleteText(v, start, length);
        }
    }

    public static void SetMaxTextSize(EditText v, int size) {
        if (frameListener != null) {
            frameListener.SetMaxTextSize(v, size);
        }
    }

    public static int GetMaxTextSize(EditText v) {
        if (frameListener != null) {
            return frameListener.GetMaxTextSize(v);
        }
        return 0;
    }

    public static int GetTextSize(EditText v) {
        if (frameListener != null) {
            return frameListener.GetTextSize(v);
        }
        return 0;
    }

    public static String GetText(EditText v) {
        if (frameListener != null) {
            return frameListener.GetText(v);
        }
        return null;
    }

    public static void SetInputType(boolean numberlic, boolean email, boolean password) {
        if (frameListener != null) {
            frameListener.SetInputType(numberlic, email, password);
        }
    }

    public static void ShowBanner(int l, int t, int w, int h, String url) {
        if (frameListener != null) {
            frameListener.ShowBanner(l, t, w, h, url);
        }
    }

    public static void HideBanner() {
        if (frameListener != null) {
            frameListener.HideBanner();
        }
    }

    public static void ShowAdmob(int l, int t, int time) {
        if (frameListener != null) {
            frameListener.ShowAdmob(l, t, time);
        }
    }

    public static void HideAdmob() {
        if (frameListener != null) {
            frameListener.HideAdmob();
        }
    }

    public static int GetAdState() {
        if (frameListener != null) {
            return frameListener.GetAdState();
        }
        return 0;
    }

    public static int CreateWAVAudioClip(String filename, int offset, int length) {
        if (audioListener != null) {
            return audioListener.CreateWAVAudioClip(filename, offset, length);
        }
        return 0;
    }

    public static int PlayWAVAudio(int id) {
        if (audioListener != null) {
            return audioListener.PlayWAVAudio(id);
        }
        return 0;
    }

    public static void StopWAVAudio(int id) {
        if (audioListener != null) {
            audioListener.StopWAVAudio(id);
        }
    }

    public static int CreateMP3AudioClip(String filename, int offset, int length) {
        if (audioListener != null) {
            return audioListener.CreateMP3AudioClip(filename, offset, length);
        }
        return 0;
    }

    public static int PlayMP3Audio(int id, boolean bloop) {
        if (audioListener != null) {
            return audioListener.PlayMP3Audio(id, bloop);
        }
        return -1;
    }

    public static void StopMP3Audio(int id) {
        if (audioListener != null) {
            audioListener.StopMP3Audio(id);
        }
    }

    public static void SetWAVVolume(int id, int volume) {
        if (audioListener != null) {
            audioListener.SetWAVVolume(id, volume);
        }
    }

    public static void SetMP3Volume(int id, int volume) {
        if (audioListener != null) {
            audioListener.SetMP3Volume(id, volume);
        }
    }

    public static void UnloadWAVAudioClip(int id) {
        if (audioListener != null) {
            audioListener.UnloadWAVAudioClip(id);
        }
    }

    public static void UnloadMP3AudioClip(int id) {
        if (audioListener != null) {
            audioListener.UnloadMP3AudioClip(id);
        }
    }

    public static int GetMP3AudioClipCurrentPosition(int id) {
        if (audioListener != null) {
            return audioListener.GetMP3AudioClipCurrentPosition(id);
        }
        return 0;
    }

    public static void SetTimer(int miliseconds) {
        if (renderListener != null) {
            renderListener.SetTimer(miliseconds);
        }
    }

    public static Font CreateDefaultFont(int size, int type) {
        return new Font(null, size, Font.FontStyle.Plain);
    }

    public static Font CreateFont(String face, int size, int type) {
        return new Font(face, size, Font.FontStyle.Plain);
    }

    public static int GetStringWidth(Font font, String str) {
        return font.getStringWidth(str);
    }

    public static void onFacebookInit(String appId, String appKey, String appSecret) {
        if (facebookListnener != null) {
            facebookListnener.onFacebookInit(appId, appKey, appSecret);
        }
    }

    public static void onLogin() {
        if (facebookListnener != null) {
            facebookListnener.onLogin();
        }
    }

    public static void onLogout() {
        if (facebookListnener != null) {
            facebookListnener.onLogout();
        }
    }

    public static void onPublish(String publishName, String publishNameLink, String publishCaption, String publishContents, String publishUrl, String publishHref, String publishLabel, String publishText, String publishLink, String publishSignText, String publishSignLink, String publishPrompt, String publishTarget) {
        if (facebookListnener != null) {
            facebookListnener.onPublish(publishName, publishNameLink, publishCaption, publishContents, publishUrl, publishHref, publishLabel, publishText, publishLink, publishSignText, publishSignLink, publishPrompt, publishTarget);
        }
    }

    public static void onQuery(String sqlQuery) {
        if (facebookListnener != null) {
            facebookListnener.onQuery(sqlQuery);
        }
    }

    public static boolean isVaild() {
        if (facebookListnener != null) {
            return facebookListnener.isVaild();
        }
        return false;
    }

    public static int CreateBitmap(int id, String filename, int offset, int length) {
        if (bitmapcreaterListener != null) {
            return bitmapcreaterListener.CreateBitmap(id, filename, offset, length);
        }
        return 0;
    }

    public static void DestoryBitmap(int id) {
        if (bitmapcreaterListener != null) {
            bitmapcreaterListener.DestoryBitmap(id);
        }
    }

    public static int CreateBitmap(int width, int height, byte[] bitdata) {
        if (bitmapcreaterListener != null) {
            return bitmapcreaterListener.CreateBitmap(width, height, bitdata);
        }
        return 0;
    }
}
