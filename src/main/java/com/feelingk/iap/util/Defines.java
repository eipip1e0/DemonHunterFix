package com.feelingk.iap.util;

public class Defines {
    public static final boolean DEBUG = true;
    public static final boolean DUMP = false;
    public static final int IAP_BPDATA_PORT = 7610;
    public static final String IAP_GW_IP = "iap.tstore.co.kr";
    public static final int IAP_GW_PORT = 7410;
    public static final String IAP_LIBRARY_VERSION = "V 11.03.22";
    public static final int IAP_PACKET_HEADER_SIZE = 12;
    public static final byte IAP_PARITY_BIT = 23;
    public static final String IAP_PROTOCOL_VERSION = "IAP10.00";
    public static final String IAP_SENDBPDATA_PROTOCOL = "SENDBPDATA";
    public static final int IAP_WIFI_PORT = 7510;
    public static final int SOCKET_CON_TIMEOUT = 20000;
    public static final int SOCKET_TIMEOUT = 20000;

    public static final class ACTION_EVENT {
        public static final int HND_AUTH_JUMINNUMBER = 1110;
        public static final int HND_ITEMAUTH_FINISH = 1109;
        public static final int HND_ITEMINFO_FINISH = 1104;
        public static final int HND_ITEMQUERY_FINISH = 1105;
        public static final int HND_ITEMUSE_FINISH = 1108;
        public static final int HND_PERMISSON_ERROR = 1115;
        public static final int HND_PURCHASE_CANCEL = 1102;
        public static final int HND_PURCHASE_CONFIRM = 1100;
        public static final int HND_PURCHASE_CONFIRM_DANAL = 1101;
        public static final int HND_PURCHASE_FINISH = 1106;
        public static final int HND_PURCHASE_FINISH_OK = 1103;
        public static final int HND_USIM_ACTIVATE_ERROR = 1116;
        public static final int HND_WHOLEQUERY_FINISH = 1107;
    }

    public static final class DIALOG_STATE {
        public static final int DLG_AUTH_DIALOG = 106;
        public static final int DLG_ERROR = 101;
        public static final int DLG_LOADING_PROGRESS = 102;
        public static final int DLG_NONE = 100;
        public static final int DLG_PURCHASE = 103;
        public static final int DLG_PURCHASE_CONFIRM = 105;
        public static final int DLG_PURCHASE_PROGRESS = 104;
    }

    public static final class IAP_GATEWAY_RESPONSE {
        public static final byte IAP_AE_SUCCESS = 0;
        public static final byte IAP_ERR_ALREADYCONNECTED = -1;
        public static final byte IAP_ERR_ALREADYINIT = -7;
        public static final byte IAP_ERR_CONNECT_FAIL = -3;
        public static final byte IAP_ERR_DISCONNECTED = -2;
        public static final byte IAP_ERR_DOWNLOADING = -9;
        public static final byte IAP_ERR_INVALIDPARITY = -8;
        public static final byte IAP_ERR_NORMAL_TIMEOUT_FAIL = -11;
        public static final byte IAP_ERR_NOTINIT = -6;
        public static final byte IAP_ERR_PARSE_FAIL = -10;
        public static final byte IAP_ERR_RECV_FAIL = -5;
        public static final byte IAP_ERR_RECV_TIMEOUT_FAIL = -13;
        public static final byte IAP_ERR_SEND_FAIL = -4;
        public static final byte IAP_ERR_SEND_TIMEOUT_FAIL = -12;
    }

    public static final class KOR_TELECOM {
        public static final int KT_TELECOM = 2;
        public static final int LG_TELECOM = 3;
        public static final int NO_TELECOM = 0;
        public static final int SK_TELECOM = 1;
    }

    public static final class KOR_TELECOM_PACKET {
        public static final String KT_TELECOM = "12";
        public static final String LG_TELECOM = "13";
        public static final String SK_TELECOM = "11";
    }

    public static final class WORK_PURCHASE_LIB_FLOW {
        public static final int ITEM_AUTH_FLOW = 4;
        public static final int ITEM_USE_FLOW = 5;
        public static final int ITEM_WHOLE_FLOW = 3;
        public static final int NONE_FLOW = 0;
        public static final int PURCHASE_FLOW_1 = 1;
        public static final int PURCHASE_FLOW_2 = 2;
    }
}
