package com.feelingk.iap;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.WindowManager;
import com.feelingk.iap.encryption.CryptoManager;
import com.feelingk.iap.gui.data.PurchaseItem;
import com.feelingk.iap.gui.parser.ParserXML;
import com.feelingk.iap.gui.view.PopJuminNumberAuth;
import com.feelingk.iap.gui.view.PopupDialog;
import com.feelingk.iap.gui.view.ProgressDialog;
import com.feelingk.iap.gui.view.PurchaseDialog;
import com.feelingk.iap.net.ItemInfoConfirm;
import com.feelingk.iap.net.MsgConfirm;
import com.feelingk.iap.util.CommonF;
import com.feelingk.iap.util.CommonString;
import com.feelingk.iap.util.Defines;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

public class IAPActivity extends Activity {
    static final String TAG = "IAPActivity";
    private int mCurTelecom = 0;
    private String mErrorMessage = null;
    private final Handler mGUIMessageHandler = new Handler() {
        /* class com.feelingk.iap.IAPActivity.AnonymousClass1 */

        public void handleMessage(Message msg) {
            int dlgType = IAPLib.getDialogType();
            CommonF.LOGGER.i(IAPActivity.TAG, "[GUI-Handler] mGUIMessageHandler msg.what= " + msg.what);
            switch (msg.what) {
                case Defines.ACTION_EVENT.HND_PURCHASE_CONFIRM /*{ENCODED_INT: 1100}*/:
                    if (dlgType == 103) {
                        IAPActivity.this.DismissPurchaseDialog();
                        IAPActivity.this.ShowLoadingProgress();
                        IAPLib.setDialogType(Defines.DIALOG_STATE.DLG_PURCHASE_PROGRESS);
                        return;
                    }
                    return;
                case Defines.ACTION_EVENT.HND_ITEMINFO_FINISH /*{ENCODED_INT: 1104}*/:
                    if (dlgType == 102) {
                        IAPActivity.this.DismissLoaingProgress();
                        IAPActivity.this.ShowPurchaseDialog(msg.obj);
                        IAPLib.setDialogType(Defines.DIALOG_STATE.DLG_PURCHASE);
                        return;
                    }
                    return;
                case Defines.ACTION_EVENT.HND_PURCHASE_FINISH /*{ENCODED_INT: 1106}*/:
                    if (dlgType == 104) {
                        IAPActivity.this.DismissLoaingProgress();
                        MsgConfirm confirm = (MsgConfirm) msg.obj;
                        try {
                            IAPActivity.this.mMsgItemInfo = new String(confirm.getMsg(), "MS949");
                            IAPActivity iAPActivity = IAPActivity.this;
                            iAPActivity.mMsgItemInfo = String.valueOf(iAPActivity.mMsgItemInfo) + "\n";
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        IAPLib.setDialogType(Defines.DIALOG_STATE.DLG_PURCHASE_CONFIRM);
                        IAPActivity.this.ShowInfoMessageDialog(Defines.DIALOG_STATE.DLG_PURCHASE_CONFIRM, IAPActivity.this.mMsgItemInfo);
                        return;
                    }
                    return;
                default:
                    CommonF.LOGGER.i(IAPActivity.TAG, "[GUI Handler] OnError " + msg.what);
                    if (dlgType != 100) {
                        IAPLib.setDialogType(100);
                        IAPActivity.this.DismissLoaingProgress();
                        IAPActivity.this.DismissPurchaseDialog();
                    }
                    if (msg.what < 2004 || msg.what > 2007) {
                        IAPActivity.this.mErrorMessage = msg.obj.toString();
                        IAPLib.setDialogType(Defines.DIALOG_STATE.DLG_ERROR);
                        IAPActivity.this.ShowInfoMessageDialog(Defines.DIALOG_STATE.DLG_ERROR, IAPActivity.this.mErrorMessage);
                        return;
                    }
                    return;
            }
        }
    };
    private ItemInfoConfirm mItemInfoConfirm = null;
    private PopJuminNumberAuth mJuminAuth = null;
    private String mMsgItemInfo = null;
    private PopupDialog mPopupDlg = null;
    private ProgressDialog mProgressDlg = null;
    private String mPurchaseBPInfo = null;
    private PurchaseDialog mPurchaseDlg = null;
    private String mPurchaseID = null;
    private int mPurchaseItemWorkFlow = 0;
    private String mPurchaseName = null;
    private int mRotaion = -1;
    private Boolean mSetBPProtocol = false;
    private Boolean mSetTmpBPProtocol = false;
    private IAPLibSetting mSetting = null;
    private boolean mTabDevice = false;
    private boolean mUseTCash = false;
    private String m_Tid = null;
    private int m_phoneUSIMState = 0;
    private TelephonyManager m_telephonyManager = null;
    View.OnClickListener onConfirmInfoListener = new View.OnClickListener() {
        /* class com.feelingk.iap.IAPActivity.AnonymousClass6 */

        public void onClick(View v) {
            IAPLib.getNetHandler().obtainMessage(Defines.ACTION_EVENT.HND_PURCHASE_FINISH_OK).sendToTarget();
            IAPActivity iAPActivity = IAPActivity.this;
            IAPActivity.this.mSetBPProtocol = false;
            iAPActivity.mSetTmpBPProtocol = false;
            IAPActivity.this.mUseTCash = false;
            IAPActivity.this.DismissInfoMessageDialog();
            IAPLib.setDialogType(100);
        }
    };
    View.OnClickListener onInfoCancelListener = new View.OnClickListener() {
        /* class com.feelingk.iap.IAPActivity.AnonymousClass5 */

        public void onClick(View v) {
            IAPLib.getNetHandler().obtainMessage(Defines.ACTION_EVENT.HND_PURCHASE_CANCEL).sendToTarget();
            IAPActivity.this.DismissInfoMessageDialog();
            IAPLib.setDialogType(100);
            IAPActivity iAPActivity = IAPActivity.this;
            IAPActivity.this.mSetBPProtocol = false;
            iAPActivity.mSetTmpBPProtocol = false;
            IAPActivity.this.mUseTCash = false;
            IAPActivity.this.mSetting.ClientListener.onDlgError();
        }
    };
    public ParserXML.ParserAuthResultCallback onJuminDialogPopupCallback = new ParserXML.ParserAuthResultCallback() {
        /* class com.feelingk.iap.IAPActivity.AnonymousClass2 */

        @Override // com.feelingk.iap.gui.parser.ParserXML.ParserAuthResultCallback
        public void onAuthDialogOKButtonClick(String juminText1, String juminText2) {
            String tmpJuminNumber;
            try {
                tmpJuminNumber = CryptoManager.encrypt(String.valueOf(juminText1) + juminText2);
            } catch (Exception e) {
                tmpJuminNumber = null;
                e.printStackTrace();
            }
            if (tmpJuminNumber != null) {
                IAPLib.getNetHandler().obtainMessage(Defines.ACTION_EVENT.HND_AUTH_JUMINNUMBER, 0, 0, tmpJuminNumber).sendToTarget();
            }
            IAPLib.setDialogType(100);
            IAPActivity.this.DismissJuminAuthDialog();
            if (tmpJuminNumber != null) {
                new Handler().postDelayed(new Runnable() {
                    /* class com.feelingk.iap.IAPActivity.AnonymousClass2.AnonymousClass1 */

                    public void run() {
                        CommonF.LOGGER.e(IAPActivity.TAG, " # Auto Runnale Mode = " + IAPActivity.this.mPurchaseItemWorkFlow);
                        if (IAPActivity.this.mPurchaseItemWorkFlow == 1) {
                            IAPActivity.this.popPurchaseDlg(IAPActivity.this.mPurchaseID, IAPActivity.this.mPurchaseName);
                        } else if (IAPActivity.this.mPurchaseItemWorkFlow == 2) {
                            IAPActivity.this.popPurchaseDlg(IAPActivity.this.mPurchaseID, IAPActivity.this.mPurchaseName, IAPActivity.this.m_Tid, IAPActivity.this.mPurchaseBPInfo);
                        } else if (IAPActivity.this.mPurchaseItemWorkFlow == 4) {
                            IAPActivity.this.sendItemAuth(IAPActivity.this.mPurchaseID);
                        } else if (IAPActivity.this.mPurchaseItemWorkFlow == 5) {
                            IAPActivity.this.sendItemUse(IAPActivity.this.mPurchaseID);
                        } else if (IAPActivity.this.mPurchaseItemWorkFlow == 3) {
                            IAPActivity.this.sendItemWholeAuth();
                        } else {
                            CommonF.LOGGER.e(IAPActivity.TAG, "# Auto Runnable Purchase Fail ");
                        }
                        IAPActivity.this.mPurchaseItemWorkFlow = 0;
                    }
                }, 300);
            }
        }

        @Override // com.feelingk.iap.gui.parser.ParserXML.ParserAuthResultCallback
        public void onAuthDialogCancelButtonClick() {
            IAPLib.setDialogType(100);
            IAPActivity.this.DismissJuminAuthDialog();
            IAPActivity.this.mPurchaseItemWorkFlow = 0;
            IAPActivity.this.mSetting.ClientListener.onJuminNumberDlgCancel();
        }
    };
    DialogInterface.OnCancelListener onProgressCancelListerner = new DialogInterface.OnCancelListener() {
        /* class com.feelingk.iap.IAPActivity.AnonymousClass4 */

        public void onCancel(DialogInterface dialog) {
            IAPLib.getNetHandler().obtainMessage(Defines.ACTION_EVENT.HND_PURCHASE_CANCEL).sendToTarget();
            IAPActivity.this.DismissLoaingProgress();
            IAPLib.setDialogType(100);
            IAPActivity.this.mUseTCash = false;
            IAPActivity iAPActivity = IAPActivity.this;
            IAPActivity.this.mSetBPProtocol = false;
            iAPActivity.mSetTmpBPProtocol = false;
            IAPActivity.this.mSetting.ClientListener.onDlgPurchaseCancel();
        }
    };
    public ParserXML.ParserResultCallback onPurchasePopupCallback = new ParserXML.ParserResultCallback() {
        /* class com.feelingk.iap.IAPActivity.AnonymousClass3 */

        @Override // com.feelingk.iap.gui.parser.ParserXML.ParserResultCallback
        public void onUseTCashCheckChanged(boolean isChecked) {
            IAPActivity.this.mUseTCash = isChecked;
            if (isChecked) {
                IAPActivity.this.mSetTmpBPProtocol = false;
            } else if (IAPActivity.this.mSetBPProtocol.booleanValue()) {
                IAPActivity.this.mSetTmpBPProtocol = true;
            } else {
                IAPActivity.this.mSetTmpBPProtocol = false;
            }
            CommonF.LOGGER.i(IAPActivity.TAG, "PopupCheck!!  UseTCash =" + isChecked + " / BPProtocol = " + IAPActivity.this.mSetTmpBPProtocol);
        }

        @Override // com.feelingk.iap.gui.parser.ParserXML.ParserResultCallback
        public void onPurchaseCancelButtonClick() {
            IAPLib.getNetHandler().obtainMessage(Defines.ACTION_EVENT.HND_PURCHASE_CANCEL).sendToTarget();
            IAPActivity.this.DismissPurchaseDialog();
            IAPLib.setDialogType(100);
            IAPActivity iAPActivity = IAPActivity.this;
            IAPActivity.this.mSetBPProtocol = false;
            iAPActivity.mSetTmpBPProtocol = false;
            IAPActivity.this.mUseTCash = false;
            IAPActivity.this.mSetting.ClientListener.onDlgPurchaseCancel();
        }

        @Override // com.feelingk.iap.gui.parser.ParserXML.ParserResultCallback
        public void onPurchaseButtonClick() {
            IAPActivity.this.mGUIMessageHandler.sendMessage(IAPActivity.this.mGUIMessageHandler.obtainMessage(Defines.ACTION_EVENT.HND_PURCHASE_CONFIRM));
            if (IAPActivity.this.mCurTelecom == 1) {
                Handler hnd = IAPLib.getNetHandler();
                CommonF.LOGGER.i(IAPActivity.TAG, "# Purchase SK!!  mUseTCash =" + IAPActivity.this.mUseTCash + " / BPProtocol = " + IAPActivity.this.mSetTmpBPProtocol);
                hnd.obtainMessage(Defines.ACTION_EVENT.HND_PURCHASE_CONFIRM, IAPActivity.this.mUseTCash ? 1 : 0, IAPActivity.this.mSetTmpBPProtocol.booleanValue() ? 1 : 0).sendToTarget();
                return;
            }
            Handler hnd2 = IAPLib.getNetHandler();
            CommonF.LOGGER.i(IAPActivity.TAG, "# Purchase KT_LG!!  ");
            hnd2.obtainMessage(Defines.ACTION_EVENT.HND_PURCHASE_CONFIRM_DANAL).sendToTarget();
        }
    };

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CommonF.LOGGER.i(TAG, "IAPActivity onCreate ");
        IAPLibDeviceCheck();
        this.m_telephonyManager = (TelephonyManager) getSystemService("phone");
        this.m_phoneUSIMState = 0;
        this.mCurTelecom = CommonF.getCarrier(this);
        if ((getWindow().getAttributes().flags & 1024) > 0) {
            this.mPurchaseDlg = new PurchaseDialog(this, 16973841, this.onPurchasePopupCallback, this.mCurTelecom, this.mTabDevice);
            this.mJuminAuth = new PopJuminNumberAuth(this, 16973841, this.onJuminDialogPopupCallback, this.mTabDevice);
            this.mPopupDlg = new PopupDialog(this, 16973841, this.mTabDevice);
        } else {
            this.mPurchaseDlg = new PurchaseDialog(this, 16973840, this.onPurchasePopupCallback, this.mCurTelecom, this.mTabDevice);
            this.mJuminAuth = new PopJuminNumberAuth(this, 16973840, this.onJuminDialogPopupCallback, this.mTabDevice);
            this.mPopupDlg = new PopupDialog(this, 16973840, this.mTabDevice);
        }
        RestoreData();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        String encJumin;
        super.onResume();
        IAPLibDeviceCheck();
        IAPLibUSIMStateCheck();
        int nDlgType = IAPLib.getDialogType();
        CommonF.LOGGER.i(TAG, "IAPActivity onResume [" + nDlgType + "]");
        IAPLib.setUIHandler(this.mGUIMessageHandler);
        if (!(this.mCurTelecom == 1 || (encJumin = IAPLib.getEncJuminNumber()) == null)) {
            IAPLib.updateEncJuminNumber(encJumin);
        }
        if (nDlgType == 103) {
            ShowPurchaseDialog(this.mItemInfoConfirm);
        } else if (nDlgType == 105) {
            ShowInfoMessageDialog(nDlgType, this.mMsgItemInfo);
        } else if (nDlgType == 106) {
            ShowJuminAuthDialog();
        } else if (nDlgType == 101) {
            ShowInfoMessageDialog(nDlgType, this.mErrorMessage);
        }
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        int nDlgType = IAPLib.getDialogType();
        CommonF.LOGGER.e(TAG, "IAPActivity onPause [" + nDlgType + "]");
        IAPLib.getNetHandler().obtainMessage(Defines.ACTION_EVENT.HND_PURCHASE_CANCEL).sendToTarget();
        if (nDlgType == 102) {
            DismissLoaingProgress();
            IAPLib.setDialogType(100);
        } else if (nDlgType == 103) {
            DismissPurchaseDialog();
        } else if (nDlgType == 105) {
            DismissInfoMessageDialog();
        } else if (nDlgType == 101) {
            DismissInfoMessageDialog();
        } else if (nDlgType == 106) {
            DismissJuminAuthDialog();
        } else if (nDlgType == 104) {
            DismissLoaingProgress();
            IAPLib.setDialogType(100);
        }
        IAPLib.setUIHandler(null);
    }

    public Object onRetainNonConfigurationInstance() {
        HashMap<String, Object> dataBackupMap = new HashMap<>();
        dataBackupMap.put("NET_MESSAGE", this.mMsgItemInfo);
        dataBackupMap.put("ERR_MESSAGE", this.mErrorMessage);
        dataBackupMap.put("USE_BPPROTOCOL", this.mSetBPProtocol);
        dataBackupMap.put("PRODUCT_NAME", this.mPurchaseName);
        dataBackupMap.put("PRODUCT_INFO", this.mItemInfoConfirm);
        return dataBackupMap;
    }

    private void RestoreData() {
        Object obj = getLastNonConfigurationInstance();
        if (obj != null) {
            HashMap<String, Object> dataBackupMap = (HashMap) obj;
            this.mMsgItemInfo = (String) dataBackupMap.get("NET_MESSAGE");
            this.mErrorMessage = (String) dataBackupMap.get("ERR_MESSAGE");
            this.mSetBPProtocol = (Boolean) dataBackupMap.get("USE_BPPROTOCOL");
            this.mPurchaseName = (String) dataBackupMap.get("PRODUCT_NAME");
            this.mItemInfoConfirm = (ItemInfoConfirm) dataBackupMap.get("PRODUCT_INFO");
            CommonF.LOGGER.i(TAG, "## Restore Data ......");
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void ShowLoadingProgress() {
        this.mProgressDlg = new ProgressDialog(this, CommonString.WORK_PROCESSING_STRING, this.onProgressCancelListerner);
        this.mProgressDlg.ShowProgress();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void DismissLoaingProgress() {
        if (this.mProgressDlg != null) {
            this.mProgressDlg.CloseProgress();
            this.mProgressDlg = null;
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void ShowInfoMessageDialog(int state, String info_message) {
        if (state == 105) {
            this.mPopupDlg.InflateView(state, info_message, this.onConfirmInfoListener);
        } else {
            this.mPopupDlg.InflateView(state, info_message, this.onInfoCancelListener);
        }
        this.mPopupDlg.ShowPopupDialog();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void DismissInfoMessageDialog() {
        this.mPopupDlg.ClosePopupDialog();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void ShowPurchaseDialog(Object item) {
        this.mItemInfoConfirm = (ItemInfoConfirm) item;
        int nPrice = Integer.parseInt(this.mItemInfoConfirm.getItemPrice().replace(",", ""));
        int nTCash = Integer.parseInt(this.mItemInfoConfirm.getItemTCash().replace(",", ""));
        IAPLibDeviceCheck();
        this.mPurchaseDlg.InflateParserDialog(this.mRotaion, new PurchaseItem(this.mPurchaseName != null ? this.mPurchaseName : this.mItemInfoConfirm.getItemTitle(), this.mItemInfoConfirm.getItemPeriod(), nPrice, nTCash, nPrice, this.mUseTCash));
        this.mPurchaseDlg.ShowPurchaseDialog();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void DismissPurchaseDialog() {
        this.mPurchaseDlg.ClosePurchaseDialog();
    }

    private void ShowJuminAuthDialog() {
        this.mJuminAuth.InflateView();
        this.mJuminAuth.ShowPopupAuthDialog();
        IAPLib.setDialogType(Defines.DIALOG_STATE.DLG_AUTH_DIALOG);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void DismissJuminAuthDialog() {
        this.mJuminAuth.ClosePopupAuthDialog();
    }

    private void IAPLibUSIMStateCheck() {
        this.m_telephonyManager.listen(new PhoneStateListener() {
            /* class com.feelingk.iap.IAPActivity.AnonymousClass7 */

            public void onServiceStateChanged(ServiceState serviceState) {
                IAPActivity.this.m_phoneUSIMState = serviceState.getState();
                if (IAPActivity.this.m_telephonyManager != null) {
                    IAPActivity.this.m_telephonyManager.listen(this, 0);
                }
            }
        }, 1);
    }

    private void IAPLibDeviceCheck() {
        this.mRotaion = ((WindowManager) getSystemService("window")).getDefaultDisplay().getOrientation();
    }

    private boolean IAPLibAuthCheck() {
        if (this.mCurTelecom == 1) {
            return false;
        }
        return IAPLib.getEncJuminNumber() == null;
    }

    public void popPurchaseDlg(String pID) {
        this.mPurchaseName = null;
        this.mSetBPProtocol = true;
        this.mSetTmpBPProtocol = true;
        popPurchaseDlg(pID, null);
    }

    /* access modifiers changed from: protected */
    public void popPurchaseDlg(String pID, String pName) {
        CommonF.LOGGER.i(TAG, "##  TStore Library Version = V 11.03.22");
        if (this.m_phoneUSIMState != 0) {
            this.mGUIMessageHandler.obtainMessage(Defines.ACTION_EVENT.HND_USIM_ACTIVATE_ERROR, CommonString.ERROR_USIM_ACTIVATE_STRING).sendToTarget();
            return;
        }
        this.mPurchaseID = pID;
        if (pName == null) {
            this.mPurchaseName = null;
        } else if (pName.contentEquals("")) {
            this.mPurchaseName = null;
        } else {
            this.mPurchaseName = pName;
        }
        String enc = null;
        if (this.mPurchaseName != null) {
            try {
                enc = URLEncoder.encode(this.mPurchaseName, "utf-8");
            } catch (UnsupportedEncodingException e) {
                enc = null;
                e.printStackTrace();
            }
        }
        if (IAPLibAuthCheck()) {
            this.mPurchaseItemWorkFlow = 1;
            ShowJuminAuthDialog();
            return;
        }
        CommonF.LOGGER.i(TAG, "# popPurchaseDlg PID= " + pID + " / UseBPProtocol=" + this.mSetBPProtocol);
        ShowLoadingProgress();
        IAPLib.setDialogType(Defines.DIALOG_STATE.DLG_LOADING_PROGRESS);
        IAPLib.sendItemInfo(pID, enc);
    }

    /* access modifiers changed from: protected */
    public void popPurchaseDlg(String pID, String pName, String pTID) {
        popPurchaseDlg(pID, pName, pTID, null);
    }

    /* access modifiers changed from: protected */
    public void popPurchaseDlg(String pID, String pName, String pTID, String pBPInfo) {
        String pEncName = null;
        String pEncBPInfo = null;
        CommonF.LOGGER.i(TAG, "##  TStore Library Version = V 11.03.22");
        if (this.m_phoneUSIMState != 0) {
            this.mGUIMessageHandler.obtainMessage(Defines.ACTION_EVENT.HND_USIM_ACTIVATE_ERROR, CommonString.ERROR_USIM_ACTIVATE_STRING).sendToTarget();
            return;
        }
        this.mPurchaseID = pID;
        this.mPurchaseBPInfo = pBPInfo;
        if (pName == null) {
            this.mPurchaseName = null;
        } else if (pName.contentEquals("")) {
            this.mPurchaseName = null;
        } else {
            this.mPurchaseName = pName;
        }
        if (this.mPurchaseName != null) {
            try {
                pEncName = URLEncoder.encode(this.mPurchaseName, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        this.m_Tid = pTID != null ? pTID : CommonF.getTID(this, pID);
        if (pBPInfo != null) {
            try {
                pEncBPInfo = URLEncoder.encode(pBPInfo, "utf-8");
            } catch (UnsupportedEncodingException e2) {
                pEncBPInfo = null;
                e2.printStackTrace();
            }
        }
        if (IAPLibAuthCheck()) {
            this.mPurchaseItemWorkFlow = 2;
            ShowJuminAuthDialog();
            return;
        }
        CommonF.LOGGER.i(TAG, "# popPurchaseDlg TID= " + pTID);
        ShowLoadingProgress();
        IAPLib.setDialogType(Defines.DIALOG_STATE.DLG_LOADING_PROGRESS);
        IAPLib.sendItemInfo(pID, pEncName, this.m_Tid, pEncBPInfo);
    }

    /* access modifiers changed from: protected */
    public void sendItemWholeAuth() {
        if (this.m_phoneUSIMState != 0) {
            this.mGUIMessageHandler.obtainMessage(Defines.ACTION_EVENT.HND_USIM_ACTIVATE_ERROR, CommonString.ERROR_USIM_ACTIVATE_STRING).sendToTarget();
        } else if (IAPLibAuthCheck()) {
            this.mPurchaseItemWorkFlow = 3;
            ShowJuminAuthDialog();
        } else {
            CommonF.LOGGER.i(TAG, "# sendItemWholeAuth");
            IAPLib.sendItemWholeAuth();
        }
    }

    /* access modifiers changed from: protected */
    public void sendItemUse(String pID) {
        if (this.m_phoneUSIMState != 0) {
            this.mGUIMessageHandler.obtainMessage(Defines.ACTION_EVENT.HND_USIM_ACTIVATE_ERROR, CommonString.ERROR_USIM_ACTIVATE_STRING).sendToTarget();
            return;
        }
        this.mPurchaseID = pID;
        if (IAPLibAuthCheck()) {
            this.mPurchaseItemWorkFlow = 5;
            ShowJuminAuthDialog();
            return;
        }
        CommonF.LOGGER.i(TAG, "# sendItemUse PID=" + pID);
        IAPLib.sendItemUse(pID);
    }

    /* access modifiers changed from: protected */
    public void sendItemAuth(String pID) {
        if (this.m_phoneUSIMState != 0) {
            this.mGUIMessageHandler.obtainMessage(Defines.ACTION_EVENT.HND_USIM_ACTIVATE_ERROR, CommonString.ERROR_USIM_ACTIVATE_STRING).sendToTarget();
            return;
        }
        this.mPurchaseID = pID;
        if (IAPLibAuthCheck()) {
            this.mPurchaseItemWorkFlow = 4;
            ShowJuminAuthDialog();
            return;
        }
        CommonF.LOGGER.i(TAG, "# sendItemAuth PID=" + pID);
        IAPLib.sendItemAuth(pID);
    }

    /* access modifiers changed from: protected */
    public byte[] sendBPData(byte[] data) {
        if (this.m_phoneUSIMState != 0) {
            this.mGUIMessageHandler.obtainMessage(Defines.ACTION_EVENT.HND_USIM_ACTIVATE_ERROR, CommonString.ERROR_USIM_ACTIVATE_STRING).sendToTarget();
            return null;
        } else if (this.mSetting.BP_IP == null || this.mSetting.BP_Port <= 1) {
            CommonF.LOGGER.i(TAG, "sendBPData - BP Server IP is null or invalid Port Number");
            return null;
        } else {
            CommonF.LOGGER.i(TAG, "# sendBPData");
            return IAPLib.sendBPData(data);
        }
    }

    public void IAPLibInit(IAPLibSetting setting) {
        this.mSetting = setting;
        IAPLib.init(this, this.mGUIMessageHandler, this.mSetting, CommonF.getMDN(this, this.mCurTelecom), this.mCurTelecom);
    }
}
