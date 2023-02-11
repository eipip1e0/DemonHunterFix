package com.feelingk.iap.util;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonF {
    static final String TAG = "Util.CommonF";
    static int m_UsimState = 0;

    public static String getMDN(Context context, int CarrierIndex) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService("phone");
        if (tm == null) {
            return null;
        }
        String phoneNumber = tm.getLine1Number();
        if (CarrierIndex == 2) {
            return convertMDN(phoneNumber);
        }
        return phoneNumber;
    }

    public static String convertMDN(String telNumber) {
        if (!telNumber.startsWith("+82", 0)) {
            return telNumber;
        }
        return String.format("0%s", telNumber.substring(3));
    }

    public static String getTID(Context context, String pid) {
        return String.format("%s_%s", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()), pid);
    }

    public static int getCarrier(Context context) {
        TelephonyManager systemService = (TelephonyManager) context.getSystemService("phone");
        if (systemService == null) {
            return 0;
        }
        String strNetworkOperator = systemService.getSimOperator();
        if (strNetworkOperator != null) {
            if (strNetworkOperator.indexOf("05") != -1) {
                return 1;
            }
            if (strNetworkOperator.indexOf("02") != -1 || strNetworkOperator.indexOf("04") != -1 || strNetworkOperator.indexOf("08") != -1) {
                return 2;
            }
            if (strNetworkOperator.indexOf("06") != -1) {
                return 3;
            }
        }
        return 0;
    }

    public static final class LOGGER {
        public static void i(String tag, String msg) {
            Log.i(tag, msg);
        }

        public static void e(String tag, String msg) {
            Log.e(tag, msg);
        }

        public static void ex(String tag, String msg) {
            Log.w(tag, "Exception : " + msg);
        }
    }
}
