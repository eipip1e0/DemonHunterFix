package com.feelingk.iap.gui.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import com.feelingk.iap.gui.parser.ParserXML;

public class PopJuminNumberAuth extends Dialog {
    private ParserXML mGUIParser = null;
    private ParserXML.ParserAuthResultCallback onResultCallback = null;

    public PopJuminNumberAuth(Context context, int themeSytle, ParserXML.ParserAuthResultCallback cb, boolean isDeviceTab) {
        super(context, themeSytle);
        this.mGUIParser = new ParserXML(context, cb, true);
        this.onResultCallback = cb;
    }

    public void InflateView() {
        setContentView(this.mGUIParser.Start("/xml_kt_lg/pop_Juminnumber.xml", null, null));
        setOnCancelListener(new DialogInterface.OnCancelListener() {
            /* class com.feelingk.iap.gui.view.PopJuminNumberAuth.AnonymousClass1 */

            public void onCancel(DialogInterface dialog) {
                PopJuminNumberAuth.this.onResultCallback.onAuthDialogCancelButtonClick();
            }
        });
    }

    public void ShowPopupAuthDialog() {
        show();
    }

    public void ClosePopupAuthDialog() {
        dismiss();
    }
}
