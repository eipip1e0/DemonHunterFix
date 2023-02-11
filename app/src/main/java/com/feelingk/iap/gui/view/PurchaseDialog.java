package com.feelingk.iap.gui.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import com.feelingk.iap.gui.data.PurchaseItem;
import com.feelingk.iap.gui.parser.ParserXML;

public class PurchaseDialog extends Dialog {
    private ParserXML mGUIParser = null;
    private ParserXML.ParserResultCallback mRetCallback = null;

    public PurchaseDialog(Context context, ParserXML.ParserResultCallback callback) {
        super(context, 16973840);
        this.mGUIParser = new ParserXML(context, callback);
        this.mRetCallback = callback;
        setOnCancelListener(new DialogInterface.OnCancelListener() {
            /* class com.feelingk.iap.gui.view.PurchaseDialog.AnonymousClass1 */

            public void onCancel(DialogInterface dialog) {
                PurchaseDialog.this.mRetCallback.onPurchaseCancelButtonClick();
            }
        });
    }

    public PurchaseDialog(Context context, int themeStyle, ParserXML.ParserResultCallback callback, int isTelecomCarrier, boolean bIsDeviceTab) {
        super(context, themeStyle);
        this.mGUIParser = new ParserXML(context, callback, isTelecomCarrier, bIsDeviceTab);
        this.mRetCallback = callback;
        setOnCancelListener(new DialogInterface.OnCancelListener() {
            /* class com.feelingk.iap.gui.view.PurchaseDialog.AnonymousClass2 */

            public void onCancel(DialogInterface dialog) {
                PurchaseDialog.this.mRetCallback.onPurchaseCancelButtonClick();
            }
        });
    }

    public void InflateParserDialog(int orientation, PurchaseItem itemInfo) {
        setContentView(this.mGUIParser.Start(orientation, itemInfo));
    }

    public void ShowPurchaseDialog() {
        show();
    }

    public void ClosePurchaseDialog() {
        dismiss();
    }
}
