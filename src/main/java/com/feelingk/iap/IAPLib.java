package com.feelingk.iap;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import com.feelingk.iap.net.IAPBase;
import com.feelingk.iap.net.InitConfirm;
import com.feelingk.iap.net.ItemAuth;
import com.feelingk.iap.net.ItemAuthInfo;
import com.feelingk.iap.net.ItemUse;
import com.feelingk.iap.net.ItemUseConfirm;
import com.feelingk.iap.net.ItemWholeAuthConfirm;
import com.feelingk.iap.util.CommonF;
import com.feelingk.iap.util.CommonString;
import com.feelingk.iap.util.Defines;

public final class IAPLib {
    public static final int HND_ERR_AUTH = 2000;
    public static final int HND_ERR_DATA = 2005;
    public static final int HND_ERR_INIT = 1999;
    public static final int HND_ERR_ITEMAUTH = 2007;
    public static final int HND_ERR_ITEMINFO = 2001;
    public static final int HND_ERR_ITEMPURCHASE = 2003;
    public static final int HND_ERR_ITEMQUERY = 2002;
    public static final int HND_ERR_NORMALTIMEOUT = 2008;
    public static final int HND_ERR_PAYMENTTIMEOUT = 2009;
    public static final int HND_ERR_SERVERTIMEOUT = 2010;
    public static final int HND_ERR_USEQUERY = 2006;
    public static final int HND_ERR_WHOLEQUERY = 2004;
    static final String TAG = "IAPLib";
    protected static String mBPInfo = null;
    protected static IAPBase mBase = null;
    protected static OnClientListener mClientListener = null;
    private static Context mContext = null;
    private static int mDialogType = 100;
    protected static String mEncJuminNumber = null;
    private static Handler mHndUI = null;
    protected static int mKorTelecom = 0;
    protected static String mMdn = null;
    protected static final Handler mNetworkMessageHandler = new Handler() {
        /* class com.feelingk.iap.IAPLib.AnonymousClass1 */

        public void handleMessage(Message msg) {
            boolean z;
            boolean z2;
            CommonF.LOGGER.i(IAPLib.TAG, "[NET-Handler] Network Message Receive Msg.what = " + msg.what);
            switch (msg.what) {
                case Defines.ACTION_EVENT.HND_PURCHASE_CONFIRM /*{ENCODED_INT: 1100}*/:
                    if (msg.arg1 == 1) {
                        z = true;
                    } else {
                        z = false;
                    }
                    IAPLib.mUseTCash = Boolean.valueOf(z);
                    if (msg.arg2 == 1) {
                        z2 = true;
                    } else {
                        z2 = false;
                    }
                    IAPLib.mUseBPProtol = Boolean.valueOf(z2);
                    IAPLib.sendItemQuery();
                    return;
                case Defines.ACTION_EVENT.HND_PURCHASE_CONFIRM_DANAL /*{ENCODED_INT: 1101}*/:
                    IAPLib.sendItemQuery();
                    return;
                case Defines.ACTION_EVENT.HND_PURCHASE_CANCEL /*{ENCODED_INT: 1102}*/:
                    IAPLib.close();
                    return;
                case Defines.ACTION_EVENT.HND_PURCHASE_FINISH_OK /*{ENCODED_INT: 1103}*/:
                    if (IAPLib.mClientListener != null) {
                        IAPLib.mClientListener.onItemPurchaseComplete();
                        return;
                    }
                    return;
                case Defines.ACTION_EVENT.HND_ITEMINFO_FINISH /*{ENCODED_INT: 1104}*/:
                    IAPLib.close();
                    if (IAPLib.mHndUI != null) {
                        IAPLib.mHndUI.obtainMessage(Defines.ACTION_EVENT.HND_ITEMINFO_FINISH, msg.obj).sendToTarget();
                        return;
                    }
                    return;
                case Defines.ACTION_EVENT.HND_ITEMQUERY_FINISH /*{ENCODED_INT: 1105}*/:
                    if (IAPLib.mClientListener == null) {
                        return;
                    }
                    if (IAPLib.mClientListener.onItemQueryComplete().booleanValue()) {
                        if (IAPLib.mKorTelecom == 1) {
                            IAPLib.sendItemPurchse(IAPLib.mUseTCash);
                            return;
                        } else {
                            IAPLib.sendItemPurchseByDanal(IAPLib.mMdn, IAPLib.mKorTelecom, IAPLib.mUseTCash, IAPLib.mEncJuminNumber);
                            return;
                        }
                    } else if (IAPLib.mHndUI != null) {
                        IAPLib.mHndUI.obtainMessage(IAPLib.HND_ERR_ITEMPURCHASE, CommonString.ERROR_PURCHASE_STRING).sendToTarget();
                        return;
                    } else {
                        return;
                    }
                case Defines.ACTION_EVENT.HND_PURCHASE_FINISH /*{ENCODED_INT: 1106}*/:
                    IAPLib.close();
                    if (IAPLib.mHndUI != null && msg.obj != null) {
                        IAPLib.mHndUI.obtainMessage(Defines.ACTION_EVENT.HND_PURCHASE_FINISH, msg.obj).sendToTarget();
                        return;
                    }
                    return;
                case Defines.ACTION_EVENT.HND_WHOLEQUERY_FINISH /*{ENCODED_INT: 1107}*/:
                    IAPLib.close();
                    ItemWholeAuthConfirm whole = IAPLib.mBase.getItemWholeAuthConfirmMessage();
                    if (IAPLib.mClientListener != null) {
                        IAPLib.mClientListener.onWholeQuery(whole.getItems());
                        return;
                    }
                    return;
                case Defines.ACTION_EVENT.HND_ITEMUSE_FINISH /*{ENCODED_INT: 1108}*/:
                    IAPLib.close();
                    ItemUseConfirm itemUse = IAPLib.mBase.getItemUseConfirmMessage();
                    ItemUse item = new ItemUse();
                    item.pId = itemUse.getItemID();
                    item.pName = itemUse.getItemName();
                    item.pCount = itemUse.getCount();
                    if (IAPLib.mClientListener != null) {
                        IAPLib.mClientListener.onItemUseQuery(item);
                        return;
                    }
                    return;
                case Defines.ACTION_EVENT.HND_ITEMAUTH_FINISH /*{ENCODED_INT: 1109}*/:
                    IAPLib.close();
                    InitConfirm init = IAPLib.mBase.getInitConfirmMessage();
                    ItemAuthInfo item2 = new ItemAuthInfo();
                    item2.pCount = init.getCount();
                    item2.pExpireDate = init.getExpireDate();
                    item2.pToken = init.getToken();
                    if (IAPLib.mClientListener != null) {
                        IAPLib.mClientListener.onItemAuthInfo(item2);
                        return;
                    }
                    return;
                case Defines.ACTION_EVENT.HND_AUTH_JUMINNUMBER /*{ENCODED_INT: 1110}*/:
                    IAPLib.mEncJuminNumber = msg.obj.toString();
                    IAPLib.mBase.setBaseEncodeJuminNumber(IAPLib.mEncJuminNumber);
                    return;
                default:
                    IAPLib.close();
                    if (msg.obj == null) {
                        msg.obj = CommonString.ERROR_NONE_PARAMETER_STRING;
                    }
                    if (msg.what == 2003 && msg.arg1 == 15) {
                        IAPLib.updateEncJuminNumber(null);
                    }
                    if (IAPLib.mHndUI != null) {
                        IAPLib.mHndUI.sendMessage(IAPLib.mHndUI.obtainMessage(msg.what, msg.obj));
                    }
                    if (IAPLib.mClientListener != null) {
                        IAPLib.mClientListener.onError(msg.what, msg.arg1);
                        return;
                    }
                    return;
            }
        }
    };
    protected static String mProductID = null;
    protected static String mProductName = null;
    protected static String mTID = null;
    protected static Boolean mUseBPProtol = false;
    protected static Boolean mUseTCash = false;

    public interface OnClientListener {
        void onDlgError();

        void onDlgPurchaseCancel();

        void onError(int i, int i2);

        void onItemAuthInfo(ItemAuthInfo itemAuthInfo);

        void onItemPurchaseComplete();

        Boolean onItemQueryComplete();

        void onItemUseQuery(ItemUse itemUse);

        void onJuminNumberDlgCancel();

        void onWholeQuery(ItemAuth[] itemAuthArr);
    }

    protected static void init(Context ctx, Handler hnd, IAPLibSetting setting, String mdn, int telecomCarrier) {
        mContext = ctx;
        mHndUI = hnd;
        mMdn = mdn;
        mKorTelecom = telecomCarrier;
        mUseBPProtol = false;
        mClientListener = setting.ClientListener;
        mBase = new IAPBase(mContext, mNetworkMessageHandler, setting, mdn);
    }

    protected static void sendItemInfo(String pID, String pName) {
        sendItemInfo(pID, pName, null, null);
    }

    protected static void sendItemInfo(String pID, String pName, String pTid) {
        sendItemInfo(pID, pName, pTid, null);
    }

    protected static void sendItemInfo(String pID, String pName, String pTid, String pBPInfo) {
        mProductID = pID;
        mProductName = pName;
        mTID = pTid;
        mBPInfo = pBPInfo;
        mBase.Reset();
        mBase.ItemInfo(mKorTelecom, pID, pName, pTid, pBPInfo);
    }

    protected static void sendItemQuery() {
        mBase.Reset();
        mBase.ItemQuery(mKorTelecom, mProductID, mProductName, mTID, mBPInfo);
    }

    protected static void sendItemPurchse(Boolean bTCash) {
        mBase.Reset();
        mBase.ItemPurchase(mProductID, mProductName, mTID, mBPInfo, bTCash, mUseBPProtol);
    }

    protected static void sendItemPurchseByDanal(String mdn, int carrier, Boolean bTCash, String jumin) {
        mBase.Reset();
        mBase.ItemPurchaseDanal(mdn, mProductID, mProductName, carrier, mTID, mBPInfo, bTCash, jumin);
    }

    protected static void sendItemWholeAuth() {
        mBase.Reset();
        mBase.ItemWholeAuth(mKorTelecom);
    }

    protected static void sendItemAuth(String pID) {
        mBase.Reset();
        mBase.ItemAuth(mKorTelecom, pID);
    }

    protected static void sendItemUse(String pID) {
        mBase.Reset();
        mBase.ItemUse(mKorTelecom, pID);
    }

    protected static byte[] sendBPData(byte[] data) {
        mBase.Reset();
        return mBase.sendBPData(data, mKorTelecom);
    }

    protected static int getDialogType() {
        return mDialogType;
    }

    protected static void setDialogType(int mDialogType2) {
        mDialogType = mDialogType2;
    }

    protected static Handler getNetHandler() {
        return mNetworkMessageHandler;
    }

    protected static Handler getUIHandler() {
        return mHndUI;
    }

    protected static void setUIHandler(Handler handler) {
        mHndUI = handler;
    }

    protected static void close() {
        mBase.StopService();
        IAPBase.close();
    }

    public static String getEncJuminNumber() {
        return mEncJuminNumber;
    }

    public static void updateEncJuminNumber(String number) {
        mEncJuminNumber = number;
        mBase.setBaseEncodeJuminNumber(number);
    }
}
