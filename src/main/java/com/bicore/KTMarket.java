package com.bicore;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import com.kt.olleh.manager.purchase.AES;
import com.kt.olleh.manager.purchase.IRemoteInAppPurchaseService;
import java.util.Map;

public class KTMarket extends BicoreActivity implements Market {
    public static final int ACCOUNT_FAIL = 998;
    public static final int GENERAL_ERROR = 999;
    public static final int INPUT_PARAM_ERROR = 100;
    public static final int KHUB_ACCOUNT_ERROR = 300;
    public static final int NETWORK_ERROR = 900;
    public static final int NETWORK_NON_WIFI = 901;
    public static final int NOT_OLLEHMARKET_APP = 800;
    public static final int SUCCESS = 0;
    public static final int USER_ACCREDIT_ERROR = 200;
    private Handler mHandler = new Handler() {
        /* class com.bicore.KTMarket.AnonymousClass1 */

        public void handleMessage(Message msg) {
            String text;
            switch (KTMarket.this.mResultCode) {
                case 0:
                    text = "결제 성공.";
                    BicoreActivity.GetActivity().runOnUiThread(new Runnable() {
                        /* class com.bicore.KTMarket.AnonymousClass1.AnonymousClass1 */

                        public void run() {
                            NativeFuntion.nativePuchaseComplete();
                        }
                    });
                    break;
                case 100:
                    text = "입력 파라미터 오류.";
                    BicoreActivity.GetActivity().runOnUiThread(new Runnable() {
                        /* class com.bicore.KTMarket.AnonymousClass1.AnonymousClass2 */

                        public void run() {
                            NativeFuntion.nativePuchaseFail(KTMarket.this.mResultCode);
                        }
                    });
                    break;
                case KTMarket.USER_ACCREDIT_ERROR /*{ENCODED_INT: 200}*/:
                    text = "사용자 인증 오류.";
                    BicoreActivity.GetActivity().runOnUiThread(new Runnable() {
                        /* class com.bicore.KTMarket.AnonymousClass1.AnonymousClass3 */

                        public void run() {
                            NativeFuntion.nativePuchaseFail(KTMarket.this.mResultCode);
                        }
                    });
                    break;
                case KTMarket.KHUB_ACCOUNT_ERROR /*{ENCODED_INT: 300}*/:
                    text = "KHUB 과금 오류.";
                    BicoreActivity.GetActivity().runOnUiThread(new Runnable() {
                        /* class com.bicore.KTMarket.AnonymousClass1.AnonymousClass4 */

                        public void run() {
                            NativeFuntion.nativePuchaseFail(KTMarket.this.mResultCode);
                        }
                    });
                    break;
                case KTMarket.NOT_OLLEHMARKET_APP /*{ENCODED_INT: 800}*/:
                    text = "올레마켓에서 구매하지 않은 어플리케이션.";
                    BicoreActivity.GetActivity().runOnUiThread(new Runnable() {
                        /* class com.bicore.KTMarket.AnonymousClass1.AnonymousClass5 */

                        public void run() {
                            NativeFuntion.nativePuchaseFail(KTMarket.this.mResultCode);
                        }
                    });
                    break;
                case KTMarket.NETWORK_ERROR /*{ENCODED_INT: 900}*/:
                    text = "네트워크 접속 오류.";
                    BicoreActivity.GetActivity().runOnUiThread(new Runnable() {
                        /* class com.bicore.KTMarket.AnonymousClass1.AnonymousClass6 */

                        public void run() {
                            NativeFuntion.nativePuchaseFail(KTMarket.this.mResultCode);
                        }
                    });
                    break;
                case KTMarket.NETWORK_NON_WIFI /*{ENCODED_INT: 901}*/:
                    text = "Wi-Fi에서는 사용할 수 없음.";
                    BicoreActivity.GetActivity().runOnUiThread(new Runnable() {
                        /* class com.bicore.KTMarket.AnonymousClass1.AnonymousClass7 */

                        public void run() {
                            NativeFuntion.nativePuchaseFail(KTMarket.this.mResultCode);
                        }
                    });
                    break;
                case KTMarket.ACCOUNT_FAIL /*{ENCODED_INT: 998}*/:
                    text = "결제를 진행하는 동안 문제가 발생했습니다.";
                    BicoreActivity.GetActivity().runOnUiThread(new Runnable() {
                        /* class com.bicore.KTMarket.AnonymousClass1.AnonymousClass8 */

                        public void run() {
                            NativeFuntion.nativePuchaseFail(KTMarket.this.mResultCode);
                        }
                    });
                    break;
                default:
                    text = "기타 일반적인 오류.";
                    BicoreActivity.GetActivity().runOnUiThread(new Runnable() {
                        /* class com.bicore.KTMarket.AnonymousClass1.AnonymousClass9 */

                        public void run() {
                            NativeFuntion.nativePuchaseFail(KTMarket.this.mResultCode);
                        }
                    });
                    break;
            }
            AlertDialog.Builder dialog = new AlertDialog.Builder(BicoreActivity.GetActivity());
            dialog.setMessage(text);
            dialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                /* class com.bicore.KTMarket.AnonymousClass1.AnonymousClass10 */

                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            dialog.create();
            dialog.show();
        }
    };
    private ProgressDialog mProgressDialog;
    private IRemoteInAppPurchaseService mRemoteInAppPurchaseService = null;
    private int mResultCode = GENERAL_ERROR;
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        /* class com.bicore.KTMarket.AnonymousClass2 */

        public void onServiceConnected(ComponentName name, IBinder service) {
            KTMarket.this.mRemoteInAppPurchaseService = IRemoteInAppPurchaseService.Stub.asInterface(service);
            Log.v("KTMarket", "Remote In App Purchase Service Connected..");
        }

        public void onServiceDisconnected(ComponentName p_name) {
            Log.v("KTMarket", "Remote In App Purchase Service Disconnected..");
            KTMarket.this.mRemoteInAppPurchaseService = null;
        }
    };

    /* access modifiers changed from: protected */
    @Override // com.bicore.BicoreActivity, com.feelingk.iap.IAPActivity
    public void onCreate(Bundle savedInstanceState) {
        Log.w("KTMarket", "*Frame - onCreate");
        super.onCreate(savedInstanceState);
        onGameInit();
    }

    @Override // com.bicore.NativeFuntion.EventListener, com.bicore.BicoreActivity
    public void OnRequestPayment(final String paycode) {
        Log.w("KTMarket", "*OnRequestPayment paycode = " + paycode);
        this.mProgressDialog = new ProgressDialog(this);
        this.mProgressDialog.setMessage("결제를 요청중입니다.");
        this.mProgressDialog.setIndeterminate(true);
        this.mProgressDialog.setProgressStyle(0);
        this.mProgressDialog.show();
        new Thread(new Runnable() {
            /* class com.bicore.KTMarket.AnonymousClass3 */
            private static final int RETRY_COUNT = 5;
            private boolean mIsInAppPurchaseConnect = false;

            public void run() {
                try {
                    this.mIsInAppPurchaseConnect = KTMarket.this.bindService(new Intent(IRemoteInAppPurchaseService.class.getName()), KTMarket.this.mServiceConnection, 1);
                    for (int i = 0; i < 5 && KTMarket.this.mRemoteInAppPurchaseService == null; i++) {
                        Log.v("KTMarket", "Remote In App Purchase Service Connecte Retry Count: " + (i + 1));
                        try {
                            Thread.sleep(100);
                        } catch (Exception e) {
                        }
                    }
                    if (KTMarket.this.mRemoteInAppPurchaseService == null) {
                        throw new NullPointerException("RemoteInAppPurchaseService Interface Create Fail..");
                    }
                    Log.v("KTMarket", "Remote In App Purchase Service Connecte Result: " + this.mIsInAppPurchaseConnect);
                    String packageName = KTMarket.this.getPackageName();
                    String iv = String.valueOf(System.currentTimeMillis());
                    Map<String, String> map = KTMarket.this.mRemoteInAppPurchaseService.purchase(packageName, iv, new AES(packageName, iv).encrypt(paycode));
                    Log.i("KTMarket", "Result Map : " + map.toString());
                    String resultIv = map.get("iv");
                    AES aes = new AES(packageName, resultIv);
                    String result = aes.decrypt(map.get("result"));
                    KTMarket.this.mResultCode = KTMarket.parseInt(aes.decrypt(map.get("result")), KTMarket.this.mResultCode);
                    Log.i("KTMarket", "IV : " + resultIv);
                    Log.i("KTMarket", "Result : " + result);
                    if (this.mIsInAppPurchaseConnect) {
                        try {
                            KTMarket.this.unbindService(KTMarket.this.mServiceConnection);
                            Log.v("KTMarket", ">> Remote In App Purchase Service Disconnecte<<");
                            this.mIsInAppPurchaseConnect = false;
                        } catch (Exception e2) {
                            Log.e("KTMarket", e2.getMessage(), e2);
                        }
                    }
                    KTMarket.this.mProgressDialog.dismiss();
                    KTMarket.this.mHandler.sendMessage(KTMarket.this.mHandler.obtainMessage());
                } catch (Exception e3) {
                    Log.e("InAppPurchaseTest", e3.getMessage(), e3);
                    KTMarket.this.mResultCode = KTMarket.ACCOUNT_FAIL;
                    if (this.mIsInAppPurchaseConnect) {
                        try {
                            KTMarket.this.unbindService(KTMarket.this.mServiceConnection);
                            Log.v("KTMarket", ">> Remote In App Purchase Service Disconnecte<<");
                            this.mIsInAppPurchaseConnect = false;
                        } catch (Exception e4) {
                            Log.e("KTMarket", e4.getMessage(), e4);
                        }
                    }
                    KTMarket.this.mProgressDialog.dismiss();
                    KTMarket.this.mHandler.sendMessage(KTMarket.this.mHandler.obtainMessage());
                } catch (Throwable th) {
                    if (this.mIsInAppPurchaseConnect) {
                        try {
                            KTMarket.this.unbindService(KTMarket.this.mServiceConnection);
                            Log.v("KTMarket", ">> Remote In App Purchase Service Disconnecte<<");
                            this.mIsInAppPurchaseConnect = false;
                        } catch (Exception e5) {
                            Log.e("KTMarket", e5.getMessage(), e5);
                        }
                    }
                    KTMarket.this.mProgressDialog.dismiss();
                    KTMarket.this.mHandler.sendMessage(KTMarket.this.mHandler.obtainMessage());
                    throw th;
                }
            }
        }).start();
    }

    public static int parseInt(String str, int defaultValue) {
        if (str == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
