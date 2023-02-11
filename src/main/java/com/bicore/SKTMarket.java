package com.bicore;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;
import com.feelingk.iap.IAPLib;
import com.feelingk.iap.IAPLibSetting;
import com.feelingk.iap.net.ItemAuth;
import com.feelingk.iap.net.ItemAuthInfo;
import com.feelingk.iap.net.ItemUse;
import com.skt.arm.aidl.IArmService;

public class SKTMarket extends BicoreActivity implements Market {
    public static String AppID = null;
    public static final int ExitApplication = 99;
    public static final int ItemAuth = 2;
    public static final int ItemUse = 4;
    public static final int Puchase = 1;
    public static final int WholeAuth = 3;
    String BP_IP = null;
    int BP_Port = 0;
    String PID = null;
    private ArmServiceConnection armCon;
    IAPLib.OnClientListener mClientListener = new IAPLib.OnClientListener() {
        /* class com.bicore.SKTMarket.AnonymousClass2 */

        @Override // com.feelingk.iap.IAPLib.OnClientListener
        public Boolean onItemQueryComplete() {
            Log.w("SKTMarket", "*onItemQueryComplete");
            return true;
        }

        @Override // com.feelingk.iap.IAPLib.OnClientListener
        public void onItemPurchaseComplete() {
            Log.w("SKTMarket", "*onItemPurchaseComplete");
            NativeFuntion.nativePuchaseComplete();
        }

        @Override // com.feelingk.iap.IAPLib.OnClientListener
        public void onWholeQuery(ItemAuth[] items) {
            Log.w("SKTMarket", "*onWholeQuery");
            String strOut = "";
            for (int i = 0; i < items.length; i++) {
                strOut = String.valueOf(strOut) + items[i].pId + " : " + items[i].pName + "\n";
            }
            SKTMarket.this.ShowToast(SKTMarket.this.getApplicationContext(), strOut);
        }

        @Override // com.feelingk.iap.IAPLib.OnClientListener
        public void onItemUseQuery(ItemUse item) {
            Log.w("SKTMarket", "*onItemUseQuery");
            SKTMarket.this.ShowToast(SKTMarket.this.getApplicationContext(), String.valueOf("") + item.pId + " : " + item.pName + " : " + item.pCount);
        }

        @Override // com.feelingk.iap.IAPLib.OnClientListener
        public void onItemAuthInfo(ItemAuthInfo itemAuth) {
            Log.w("SKTMarket", "*onItemAuthInfo");
            SKTMarket.this.ShowToast(SKTMarket.this.getApplicationContext(), String.valueOf("") + SKTMarket.this.PID + " : " + itemAuth.pCount + " : " + new String(itemAuth.pExpireDate));
            if (itemAuth.pToken != null) {
                Log.i("Sample", new String(itemAuth.pToken));
            }
        }

        @Override // com.feelingk.iap.IAPLib.OnClientListener
        public void onError(int ErrorCode, int arg1) {
            Log.w("SKTMarket", "*onError" + ErrorCode);
            switch (ErrorCode) {
            }
            NativeFuntion.nativePuchaseFail(ErrorCode);
        }

        public void onTokenReceive(byte[] token) {
            Log.w("SKTMarket", "*onTokenReceive");
        }

        @Override // com.feelingk.iap.IAPLib.OnClientListener
        public void onDlgError() {
        }

        @Override // com.feelingk.iap.IAPLib.OnClientListener
        public void onDlgPurchaseCancel() {
            Log.w("SKTMarket", "*onDlgPucgaseCancel");
            NativeFuntion.nativePuchaseCancel();
        }

        @Override // com.feelingk.iap.IAPLib.OnClientListener
        public void onJuminNumberDlgCancel() {
            Log.w("SKTMarket", "*onJuminNumberDlgCancel");
            NativeFuntion.nativePuchaseCancel();
        }
    };
    Handler mHandler = new Handler() {
        /* class com.bicore.SKTMarket.AnonymousClass1 */

        public void handleMessage(Message msg) {
            Log.w("SKTMarket", "*handleMessage - Message :" + msg);
            if (msg.what == 1) {
                SKTMarket.this.popPurchaseDlg((String) msg.obj);
            } else if (msg.what == 2) {
                SKTMarket.this.sendItemAuth((String) msg.obj);
            } else if (msg.what == 3) {
                SKTMarket.this.sendItemWholeAuth();
            } else if (msg.what == 4) {
                SKTMarket.this.sendItemUse((String) msg.obj);
            } else {
                int i = msg.what;
            }
        }
    };
    private String resMsg;
    private IArmService service;

    /* access modifiers changed from: protected */
    @Override // com.bicore.BicoreActivity, com.feelingk.iap.IAPActivity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.w("SKTMarket", "*Frame - onCreate");
        IAPLibSetting setting = new IAPLibSetting();
        setting.AppID = AppID;
        setting.BP_IP = this.BP_IP;
        setting.BP_Port = this.BP_Port;
        setting.ClientListener = this.mClientListener;
        IAPLibInit(setting);
        onGameInit();
    }

    /* access modifiers changed from: package-private */
    public class ArmServiceConnection implements ServiceConnection {
        ArmServiceConnection() {
        }

        public void onServiceConnected(ComponentName name, IBinder boundService) {
            if (SKTMarket.this.service == null) {
                SKTMarket.this.service = IArmService.Stub.asInterface(boundService);
            }
            try {
                int res = SKTMarket.this.service.executeArm(SKTMarket.AppID);
                if (res == 1) {
                    SKTMarket.this.onGameInit();
                } else {
                    switch (res) {
                        case -268435452:
                            SKTMarket.this.resMsg = "일시적인 장애로 라이선스 발급에 실패하였습니다.잠시후에 다시 시도해 주십시요.(04)";
                            break;
                        case -268435451:
                        case -268435450:
                        case -268435449:
                        case -268435445:
                        case -268435441:
                        case -268435440:
                        default:
                            SKTMarket.this.resMsg = "알수없는 에러가 발생 했습니다. 결과 코드=" + Integer.toHexString(res);
                            break;
                        case -268435448:
                            SKTMarket.this.resMsg = "일시적인 장애로 라이선스 발급에 실패하였습니다.잠시후에 다시 시도해 주십시요.(08)";
                            break;
                        case -268435447:
                            SKTMarket.this.resMsg = "상품 구매내역 확인에 실패하였습니다.자세한 사항은 고객센터로 문의 바랍니다.(09)";
                            break;
                        case -268435446:
                            SKTMarket.this.resMsg = "Tstore 미가입된 단말입니다.가입 후 이용을 해주시기 바랍니다.(0A)";
                            break;
                        case -268435444:
                            SKTMarket.this.resMsg = "일시적인 장애로 라이선스 발급에 실패하였습니다.잠시후에 다시 시도해 주십시요.(0C)";
                            break;
                        case -268435443:
                            SKTMarket.this.resMsg = "어플리케이션의 라이선스 정보 확인이 불가능합니다.자세한 사항은 고객센터로 문의 바랍니다.(0D)";
                            break;
                        case -268435442:
                            SKTMarket.this.resMsg = "일시적인 장애로 라이선스 발급에 실패하였습니다.잠시후에 다시 시도해 주십시요.(0E)";
                            break;
                        case -268435439:
                            SKTMarket.this.resMsg = "핸드폰 번호를 확인할 수 없습니다.USIM 장착여부 확인 및 USIM 잠금이 된 경우 해제를 해주시기 바랍니다. (11)";
                            break;
                        case -268435438:
                            SKTMarket.this.resMsg = "어플리케이션의 정보 확인이 불가능합니다.자세한 사항은 고객센터로 문의 바랍니다.(12)";
                            break;
                        case -268435437:
                            SKTMarket.this.resMsg = "핸드폰에서 데이타통신(3G, WIFI)이 되고 있지 않습니다.핸드폰의 데이터 통신 설정부분을 확인 후 재 실행을 해 주십시요. (13)";
                            break;
                        case -268435436:
                            SKTMarket.this.resMsg = "Tstore 전용프로그램이 설치 되어 있지 않습니다.Tstore 전용프로그램을 설치하신 후 재 실행을 해 주십시요. (14)";
                            break;
                    }
                    SKTMarket.this.showDialog(0);
                }
                SKTMarket.this.releaseService();
            } catch (Exception e) {
                e.printStackTrace();
                SKTMarket.this.releaseService();
                SKTMarket.this.resMsg = "서비스 실행 실패";
                SKTMarket.this.showDialog(0);
            }
        }

        public void onServiceDisconnected(ComponentName name) {
            SKTMarket.this.service = null;
        }
    }

    private boolean runService() {
        try {
            if (this.armCon == null) {
                this.armCon = new ArmServiceConnection();
                if (bindService(new Intent(IArmService.class.getName()), this.armCon, 1)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        releaseService();
        return false;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void releaseService() {
        if (this.armCon != null) {
            unbindService(this.armCon);
            this.armCon = null;
            this.service = null;
        }
    }

    /* access modifiers changed from: protected */
    public Dialog onCreateDialog(int id) {
        return new AlertDialog.Builder(this).setTitle("알 림").setMessage(this.resMsg).setPositiveButton("확인", new DialogInterface.OnClickListener() {
            /* class com.bicore.SKTMarket.AnonymousClass3 */

            public void onClick(DialogInterface dialog, int whichButton) {
                SKTMarket.this.finish();
            }
        }).setOnCancelListener(new DialogInterface.OnCancelListener() {
            /* class com.bicore.SKTMarket.AnonymousClass4 */

            public void onCancel(DialogInterface dialog) {
                SKTMarket.this.finish();
            }
        }).setOnKeyListener(new DialogInterface.OnKeyListener() {
            /* class com.bicore.SKTMarket.AnonymousClass5 */

            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return true;
            }
        }).create();
    }

    public void ShowToast(Context context, String str) {
        Log.w("SKTMarket", "*ShowToast");
        Toast.makeText(context, str, 1).show();
    }

    @Override // com.bicore.NativeFuntion.EventListener, com.bicore.BicoreActivity
    public void OnRequestPayment(String paycode) {
        Message msg = new Message();
        msg.obj = new String(paycode);
        msg.what = 1;
        Log.w("SKTMarket", "*OnRequestPayment paycode = " + paycode);
        this.mHandler.sendMessage(msg);
    }
}
