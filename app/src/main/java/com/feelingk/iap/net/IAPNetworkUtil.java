package com.feelingk.iap.net;

public class IAPNetworkUtil {
    private static final String TAG = "IAPNet";

    public static byte[] iapMakePacketHeader(String packet_type, int datalen) {
        byte[] buf = new byte[12];
        for (int i = 0; i < 12; i++) {
            buf[i] = 0;
        }
        System.arraycopy(packet_type.getBytes(), 0, buf, 0, 2);
        byte[] data_len = String.valueOf(datalen).getBytes();
        System.arraycopy(data_len, 0, buf, 2, data_len.length);
        return buf;
    }

    public static void packetDebug(byte[] packetBytes, int length) {
    }
}
