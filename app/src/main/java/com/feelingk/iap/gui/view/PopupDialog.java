package com.feelingk.iap.gui.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import com.feelingk.iap.gui.parser.ParserXML;

public class PopupDialog extends Dialog {
    private View.OnClickListener mClickListener = null;
    private ParserXML mGUIParser = null;

    public PopupDialog(Context context, int themeStyle, boolean isDeviceTab) {
        super(context, themeStyle);
        this.mGUIParser = new ParserXML(context);
    }

    public void InflateView(int state, String message, View.OnClickListener clickListener) {
        this.mClickListener = clickListener;
        setContentView(this.mGUIParser.Start("/xml/commonPopup.xml", message, clickListener));
        if (state == 105) {
            setOnCancelListener(new DialogInterface.OnCancelListener() {
                /* class com.feelingk.iap.gui.view.PopupDialog.AnonymousClass1 */

                public void onCancel(DialogInterface dialog) {
                    PopupDialog.this.mClickListener.onClick(null);
                }
            });
        }
    }

    public void ShowPopupDialog() {
        show();
    }

    public void ClosePopupDialog() {
        dismiss();
    }
}
