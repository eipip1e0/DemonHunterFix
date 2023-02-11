package com.facebook.stream;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

public class App extends Activity {
    public static final String FB_APP_ID = "126642314059639";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (FB_APP_ID == 0) {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
            alertBuilder.setTitle("Warning");
            alertBuilder.setMessage("A Facebook Applicaton ID must be specified before running this example: see App.java");
            alertBuilder.create().show();
        }
        Dispatcher dispatcher = new Dispatcher(this);
        dispatcher.addHandler("login", LoginHandler.class);
        dispatcher.addHandler("stream", StreamHandler.class);
        dispatcher.addHandler("logout", LogoutHandler.class);
        if (Session.restore(this) != null) {
            dispatcher.runHandler("stream");
        } else {
            dispatcher.runHandler("login");
        }
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Session.wakeupForAuthCallback().authorizeCallback(requestCode, resultCode, data);
    }
}
