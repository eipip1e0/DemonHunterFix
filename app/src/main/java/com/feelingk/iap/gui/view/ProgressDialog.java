package com.feelingk.iap.gui.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ProgressDialog {
    private Dialog dialog = null;

    public ProgressDialog(Context context, String message, DialogInterface.OnCancelListener onCancelLisener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LinearLayout dialogLayout = new LinearLayout(context);
        dialogLayout.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        TextView tv = new TextView(context);
        tv.setGravity(17);
        tv.setLayoutParams(new LinearLayout.LayoutParams(-2, -1));
        tv.setTextColor(-1);
        tv.setTextSize(16.0f);
        tv.setPadding(10, 10, 10, 10);
        tv.setText(message);
        ProgressBar pgBar = new ProgressBar(context);
        pgBar.setPadding(10, 10, 10, 10);
        dialogLayout.addView(pgBar);
        dialogLayout.addView(tv);
        builder.setView(dialogLayout);
        this.dialog = builder.create();
        this.dialog.setOnCancelListener(onCancelLisener);
    }

    public void ShowProgress() {
        this.dialog.show();
    }

    public void CloseProgress() {
        this.dialog.setOnCancelListener(null);
        this.dialog.dismiss();
    }
}
