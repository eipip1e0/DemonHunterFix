package com.feelingk.iap.net;

import com.feelingk.iap.util.CommonF;
import com.feelingk.iap.util.CommonString;
import com.feelingk.iap.util.Defines;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.security.SecureRandom;
import javax.net.ssl.SSLContext;

public class IAPNet {
    static final String TAG = "IAPNet";
    private static boolean connect = false;
    private static boolean connectBP = false;
    private static InputStream inputStream = null;
    private static InputStream inputStreamBP = null;
    private static boolean isInit = false;
    private static boolean isWifi = false;
    private static OutputStream outputStream = null;
    private static OutputStream outputStreamBP = null;
    private static Socket socketBP = null;
    private static Socket socketGateway = null;

    /* JADX INFO: Multiple debug info for r10v24 javax.net.ssl.SSLSocketFactory: [D('FACTORY' javax.net.ssl.SSLSocketFactory), D('sslcontext' javax.net.ssl.SSLContext)] */
    protected static InitConfirm iapConnect(ServerInfo info, int telecom, String mcid, String min, String bpServerIP, int bpServerPort, String pID, String pTID, String pEncJumin, boolean useBpServer) {
        InitConfirm initConfirm = new InitConfirm();
        CommonF.LOGGER.i(TAG, "[ DEBUG ] Server connect - Start : iapConnect Wifi= " + isWifi);
        if (connect) {
            initConfirm.setResultCode((byte) -1);
        } else {
            try {
                if (isWifi) {
                    SocketAddress socketAddress = new InetSocketAddress(info.getHostAddress(), info.getPort());
                    SSLContext sslcontext = SSLContext.getInstance("TLS");
                    sslcontext.init(null, new TrustManager[]{new TrustManager()}, new SecureRandom());
                    socketGateway = sslcontext.getSocketFactory().createSocket();
                    socketGateway.setSoTimeout(20000);
                    socketGateway.connect(socketAddress, 20000);
                    inputStream = socketGateway.getInputStream();
                    outputStream = socketGateway.getOutputStream();
                } else {
                    socketGateway = new Socket();
                    socketGateway.setSoTimeout(20000);
                    socketGateway.connect(new InetSocketAddress(info.getHostAddress(), info.getPort()), 20000);
                    inputStream = socketGateway.getInputStream();
                    outputStream = socketGateway.getOutputStream();
                }
                connect = true;
                CommonF.LOGGER.i(TAG, "[ DEBUG ] Server connect - Complete!!!");
                iapSendInit(initConfirm, telecom, mcid, pEncJumin, min, bpServerIP, bpServerPort, pID, pTID, useBpServer);
                if (initConfirm.getResultCode() != 0) {
                    if (initConfirm.getResultCode() != -7) {
                        isInit = false;
                    }
                    if (initConfirm.getResultCode() == -5 || initConfirm.getResultCode() == -4) {
                        initConfirm.SetUserMessage(CommonString.ERROR_NETWORK_CONNECT_CHECK_STRING);
                    }
                } else {
                    byte[] recv = iapReceive(initConfirm);
                    if (initConfirm.getResultCode() != 0) {
                        isInit = false;
                        initConfirm.SetUserMessage(CommonString.ERROR_NETWORK_SEND_RECV_CHECK_STRING);
                    } else {
                        initConfirm.parse(recv);
                    }
                }
            } catch (SocketTimeoutException e) {
                connect = false;
                initConfirm.setResultCode(Defines.IAP_GATEWAY_RESPONSE.IAP_ERR_NORMAL_TIMEOUT_FAIL);
                initConfirm.SetUserMessage(CommonString.ERROR_NETWORK_CONNECT_CHECK_STRING);
                e.printStackTrace();
            } catch (IOException e2) {
                connect = false;
                CommonF.LOGGER.i(TAG, "[ Exception ] iapConnect() DUMP :" + e2.toString());
                initConfirm.setResultCode((byte) -3);
                initConfirm.SetUserMessage(CommonString.ERROR_NETWORK_CONNECT_CHECK_STRING);
                e2.printStackTrace();
            } catch (Exception e3) {
                CommonF.LOGGER.i(TAG, "[ Exception ] iapConnect() DUMP :" + e3.toString());
                connect = false;
                initConfirm.setResultCode((byte) -3);
                initConfirm.SetDumpMessage(e3.toString());
                e3.printStackTrace();
            }
        }
        return initConfirm;
    }

    protected static int iapClose(boolean isResRelease) {
        CommonF.LOGGER.e(TAG, "[ DEBUG ] Socket Close!");
        try {
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (Exception e) {
        }
        try {
            if (outputStream != null) {
                outputStream.close();
            }
        } catch (Exception e2) {
        }
        try {
            if (socketGateway != null) {
                socketGateway.close();
            }
        } catch (Exception e3) {
        }
        inputStream = null;
        outputStream = null;
        socketGateway = null;
        connect = false;
        isInit = false;
        return 0;
    }

    /* JADX INFO: Multiple debug info for r11v19 byte[]: [D('header' byte[]), D('i' int)] */
    /* JADX INFO: Multiple debug info for r11v20 int: [D('header' byte[]), D('offset' int)] */
    /* JADX INFO: Multiple debug info for r7v10 int: [D('offset' int), D('byte_pTelecom' byte[])] */
    protected static void iapSendInit(InitConfirm initConfirm, int pTelecom, String applicationID, String encJuminNumber, String MIN, String bpServerIP, int bpServerPort, String pID, String pTID, boolean useBpServer) {
        String sHeaderPacket;
        int offset;
        CommonF.LOGGER.i(TAG, "iapSendInit  Start() isInit=" + isInit);
        CommonF.LOGGER.i(TAG, "iapSendInit  Start() useBpServer =" + useBpServer);
        CommonF.LOGGER.i(TAG, "iapSendInit  Start() encJuminNumberCheck =" + (encJuminNumber != null ? 1 : 0));
        if (isInit) {
            initConfirm.setResultCode((byte) -7);
            return;
        }
        byte[] ipBytes = bpServerIP != null ? bpServerIP.getBytes() : null;
        byte[] portBytes = String.valueOf(bpServerPort).getBytes();
        int packet_len = 12;
        if (pTID != null) {
            packet_len = 12 + 100;
            sHeaderPacket = "QP";
        } else {
            sHeaderPacket = "IP";
        }
        int packet_len2 = packet_len + 1 + 8 + 1 + 10 + 11 + 1;
        if (ipBytes != null) {
            packet_len2 += ipBytes.length;
        }
        int packet_len3 = packet_len2 + 7 + 10;
        if (pTelecom == 2 || pTelecom == 3) {
            int packet_len4 = packet_len3 + 2;
            if (useBpServer) {
                packet_len3 = packet_len4 + 1 + Defines.IAP_SENDBPDATA_PROTOCOL.length();
            } else {
                packet_len3 = packet_len4 + 1 + encJuminNumber.length();
            }
        }
        int packet_len5 = packet_len3 + 1;
        byte[] packetBytes = new byte[packet_len5];
        for (int i = 0; i < packet_len5; i++) {
            packetBytes[i] = 0;
        }
        byte[] header = IAPNetworkUtil.iapMakePacketHeader(sHeaderPacket, packet_len5 - 12);
        System.arraycopy(header, 0, packetBytes, 0, header.length);
        int offset2 = 0 + 12;
        int offset3 = offset2 + 1;
        packetBytes[offset2] = 71;
        System.arraycopy(Defines.IAP_PROTOCOL_VERSION.getBytes(), 0, packetBytes, offset3, 8);
        int offset4 = offset3 + 8;
        int offset5 = offset4 + 1;
        packetBytes[offset4] = 65;
        System.arraycopy(applicationID.getBytes(), 0, packetBytes, offset5, Math.min(applicationID.getBytes().length, 10));
        int offset6 = offset5 + 10;
        System.arraycopy(MIN.getBytes(), 0, packetBytes, offset6, Math.min(MIN.getBytes().length, 11));
        int offset7 = offset6 + 11;
        if (ipBytes != null) {
            packetBytes[offset7] = (byte) ipBytes.length;
        }
        int offset8 = offset7 + 1;
        if (ipBytes != null) {
            System.arraycopy(ipBytes, 0, packetBytes, offset8, ipBytes.length);
            offset8 = ipBytes.length + 44;
            System.arraycopy(portBytes, 0, packetBytes, offset8, portBytes.length);
        }
        int offset9 = offset8 + 7;
        if (pID != null) {
            byte[] byte_pID = String.valueOf(pID).getBytes();
            if (byte_pID.length <= 10) {
                System.arraycopy(byte_pID, 0, packetBytes, offset9, byte_pID.length);
            }
        }
        int offset10 = offset9 + 10;
        if (pTID != null) {
            byte[] byte_pTID = String.valueOf(pTID).getBytes();
            System.arraycopy(byte_pTID, 0, packetBytes, offset10, byte_pTID.length);
            offset = offset10 + 100;
        } else {
            offset = offset10;
        }
        if (pTelecom == 2 || pTelecom == 3) {
            byte[] byte_pTelecom = String.valueOf(pTelecom == 2 ? Defines.KOR_TELECOM_PACKET.KT_TELECOM : Defines.KOR_TELECOM_PACKET.LG_TELECOM).getBytes();
            System.arraycopy(byte_pTelecom, 0, packetBytes, offset, byte_pTelecom.length);
            int offset11 = offset + 2;
            if (useBpServer) {
                int offset12 = offset11 + 1;
                packetBytes[offset11] = (byte) Defines.IAP_SENDBPDATA_PROTOCOL.length();
                byte[] byte_pSendBPData = Defines.IAP_SENDBPDATA_PROTOCOL.getBytes();
                System.arraycopy(byte_pSendBPData, 0, packetBytes, offset12, byte_pSendBPData.length);
                int length = byte_pSendBPData.length + offset12;
            } else {
                int offset13 = offset11 + 1;
                packetBytes[offset11] = (byte) encJuminNumber.length();
                byte[] byte_pJumin = encJuminNumber.getBytes();
                System.arraycopy(byte_pJumin, 0, packetBytes, offset13, byte_pJumin.length);
                int length2 = encJuminNumber.getBytes().length + offset13;
            }
        }
        packetBytes[packet_len5 - 1] = Defines.IAP_PARITY_BIT;
        byte ret = iapWrite(packetBytes);
        if (ret != 0) {
            initConfirm.setResultCode(ret);
            initConfirm.SetUserMessage(CommonString.ERROR_NETWORK_SEND_RECV_CHECK_STRING);
            return;
        }
        initConfirm.setResultCode((byte) 0);
        isInit = true;
    }

    protected static InitConfirm iapReAuth(int pTelecom, String applicationID, String pJuminNumber, String MDN, String bpServerIP, int bpServerPort, String pID, String pTID) {
        InitConfirm initConfirm = new InitConfirm();
        isInit = false;
        iapSendInit(initConfirm, pTelecom, applicationID, pJuminNumber, MDN, bpServerIP, bpServerPort, pID, pTID, false);
        if (initConfirm.getResultCode() == 0) {
            byte[] recv = iapReceive(initConfirm);
            if (initConfirm.getResultCode() != 0) {
                initConfirm.SetUserMessage(CommonString.ERROR_NETWORK_SEND_RECV_CHECK_STRING);
            } else {
                initConfirm.parse(recv);
            }
        }
        return initConfirm;
    }

    protected static synchronized byte iapWrite(byte[] sendBuf) {
        byte b;
        synchronized (IAPNet.class) {
            CommonF.LOGGER.e(TAG, "[ DEBUG ] iapWrite connect(" + connect + ")");
            if (!connect) {
                b = -2;
            } else {
                IAPNetworkUtil.packetDebug(sendBuf, sendBuf.length);
                try {
                    outputStream.write(sendBuf, 0, sendBuf.length);
                    outputStream.flush();
                    CommonF.LOGGER.e(TAG, "[ DEBUG ] iapWrite End  ");
                    b = 0;
                } catch (SocketTimeoutException e) {
                    CommonF.LOGGER.i(TAG, "[ Exception ] iapWrite() " + e);
                    e.printStackTrace();
                    b = Defines.IAP_GATEWAY_RESPONSE.IAP_ERR_SEND_TIMEOUT_FAIL;
                } catch (IOException e2) {
                    CommonF.LOGGER.i(TAG, "[ Exception ] iapWrite() " + e2);
                    b = -4;
                }
            }
        }
        return b;
    }

    private static synchronized byte[] iapReceive(Confirm confirm) {
        byte[] bArr;
        synchronized (IAPNet.class) {
            CommonF.LOGGER.e(TAG, "[ DEBUG ] iapReceive() Header Start (" + isInit + ")");
            if (!isInit) {
                confirm.setResultCode((byte) -6);
                bArr = null;
            } else {
                int receivedBytes = 0;
                try {
                    byte[] header = new byte[12];
                    while (true) {
                        if (receivedBytes >= 12) {
                            break;
                        }
                        int reads = inputStream.read(header, receivedBytes, header.length - receivedBytes);
                        if (reads != -1) {
                            receivedBytes += reads;
                        } else if (reads == -1) {
                            confirm.setResultCode((byte) -5);
                            bArr = null;
                        }
                    }
                    String datalength = new String(header, 2, 10).trim();
                    if (datalength.length() > 0) {
                        int data_len = Integer.parseInt(datalength);
                        byte[] data = new byte[data_len];
                        int receivedBytes2 = 0;
                        while (receivedBytes2 < data_len) {
                            int reads2 = inputStream.read(data, receivedBytes2, data.length - receivedBytes2);
                            if (reads2 == -1) {
                                break;
                            }
                            receivedBytes2 += reads2;
                        }
                        if (23 != data[data_len - 1]) {
                            CommonF.LOGGER.e(TAG, "[ DEBUG ] IAP_ERR_INVALIDPARITY =");
                            confirm.setResultCode((byte) -8);
                            bArr = null;
                        } else {
                            byte[] returnData = new byte[(header.length + data.length)];
                            System.arraycopy(header, 0, returnData, 0, header.length);
                            System.arraycopy(data, 0, returnData, header.length, data.length - 1);
                            if (confirm != null) {
                                confirm.setResultCode((byte) 0);
                            }
                            IAPNetworkUtil.packetDebug(returnData, header.length + data.length);
                            CommonF.LOGGER.e(TAG, "[ DEBUG ] iapReceive() Success!!  ");
                            bArr = returnData;
                        }
                    } else {
                        confirm.setResultCode((byte) -5);
                        bArr = null;
                    }
                } catch (SocketTimeoutException e) {
                    CommonF.LOGGER.i(TAG, "[ Exception ] iapReceive() " + e);
                    confirm.setResultCode(Defines.IAP_GATEWAY_RESPONSE.IAP_ERR_RECV_TIMEOUT_FAIL);
                    bArr = null;
                } catch (Exception e2) {
                    CommonF.LOGGER.i(TAG, "[ Exception ] iapReceive() " + e2);
                    confirm.setResultCode((byte) -5);
                    bArr = null;
                }
            }
        }
        return bArr;
    }

    /* JADX INFO: Multiple debug info for r1v9 javax.net.ssl.SSLSocketFactory: [D('context' javax.net.ssl.SSLContext), D('FACTORY' javax.net.ssl.SSLSocketFactory)] */
    protected static InitConfirm iapConnectBP(ServerInfo info, int telecom, String mcid, String min, String bpServerIP, int bpServerPort) {
        InitConfirm init = new InitConfirm();
        CommonF.LOGGER.i(TAG, "[ DEBUG ] iapConnectBP - Start! isWifi= " + isWifi);
        if (connectBP) {
            init.setResultCode((byte) -1);
        } else {
            try {
                if (isWifi) {
                    SSLContext context = SSLContext.getInstance("TLS");
                    TrustManager[] trustManagerArr = null;
                    context.init(null, new TrustManager[]{new TrustManager()}, new SecureRandom());
                    socketBP = context.getSocketFactory().createSocket();
                } else {
                    socketBP = new Socket();
                }
                socketBP.connect(new InetSocketAddress(info.getHostAddress(), info.getPort()), 10000);
                inputStreamBP = socketBP.getInputStream();
                outputStreamBP = socketBP.getOutputStream();
                connectBP = true;
                CommonF.LOGGER.i(TAG, "[ DEBUG ] iapConnectBP - Connection Complete!");
                iapSendInitBP(init, telecom, mcid, min, bpServerIP.getBytes(), bpServerPort);
                if (init.getResultCode() == 0 || init.getResultCode() == -7) {
                    byte[] recv = iapReceiveBP(init);
                    if (init.getResultCode() != 0) {
                        init.SetUserMessage(CommonString.ERROR_NETWORK_CONNECT_CHECK_STRING);
                    } else {
                        init.parse(recv);
                    }
                } else {
                    init.SetUserMessage(CommonString.ERROR_NETWORK_CONNECT_CHECK_STRING);
                }
            } catch (Exception e) {
                CommonF.LOGGER.i(TAG, "[ Exception ] iapConnectBP() " + e);
                connectBP = false;
                init.setResultCode((byte) -3);
                init.SetUserMessage(CommonString.ERROR_NETWORK_CONNECT_CHECK_STRING);
            }
        }
        return init;
    }

    protected static int iapCloseBP(boolean isResRelease) {
        try {
            if (inputStreamBP != null) {
                inputStreamBP.close();
            }
        } catch (Exception e) {
        }
        try {
            if (outputStreamBP != null) {
                outputStreamBP.close();
            }
        } catch (Exception e2) {
        }
        try {
            if (socketBP != null) {
                socketBP.close();
            }
        } catch (Exception e3) {
        }
        inputStreamBP = null;
        outputStreamBP = null;
        socketBP = null;
        connectBP = false;
        return 0;
    }

    /* JADX INFO: Multiple debug info for r11v18 int: [D('header' byte[]), D('offset' int)] */
    /* JADX INFO: Multiple debug info for r7v10 int: [D('byte_pTelecom' byte[]), D('offset' int)] */
    protected static void iapSendInitBP(InitConfirm init, int pTelecom, String applicationID, String MIN, byte[] ip, int port) {
        byte[] portBytes = String.valueOf(port).getBytes();
        CommonF.LOGGER.i(TAG, "[ DEBUG ] iapSendInitBP()  __ Start");
        int packet_len = 12 + 1 + 8 + 1 + 10 + 11 + 1;
        if (ip != null) {
            packet_len = ip.length + 44;
        }
        int packet_len2 = packet_len + 7 + 10;
        if (pTelecom == 2 || pTelecom == 3) {
            packet_len2 = packet_len2 + 2 + 1 + Defines.IAP_SENDBPDATA_PROTOCOL.length();
        }
        int packet_len3 = packet_len2 + 1;
        byte[] packetBytes = new byte[packet_len3];
        for (int i = 0; i < packet_len3; i++) {
            packetBytes[i] = 0;
        }
        byte[] header = IAPNetworkUtil.iapMakePacketHeader("IP", packet_len3 - 12);
        System.arraycopy(header, 0, packetBytes, 0, header.length);
        int offset = 0 + 12;
        int offset2 = offset + 1;
        packetBytes[offset] = 71;
        System.arraycopy(Defines.IAP_PROTOCOL_VERSION.getBytes(), 0, packetBytes, offset2, 8);
        int offset3 = offset2 + 8;
        int offset4 = offset3 + 1;
        packetBytes[offset3] = 65;
        System.arraycopy(applicationID.getBytes(), 0, packetBytes, offset4, Math.min(applicationID.getBytes().length, 10));
        int offset5 = offset4 + 10;
        System.arraycopy(MIN.getBytes(), 0, packetBytes, offset5, Math.min(MIN.getBytes().length, 11));
        int offset6 = offset5 + 11;
        if (ip != null) {
            packetBytes[offset6] = (byte) ip.length;
        }
        int offset7 = offset6 + 1;
        if (ip != null) {
            System.arraycopy(ip, 0, packetBytes, offset7, ip.length);
            offset7 = ip.length + 44;
            System.arraycopy(portBytes, 0, packetBytes, offset7, portBytes.length);
        }
        int offset8 = offset7 + 7 + 10;
        if (pTelecom == 2 || pTelecom == 3) {
            byte[] byte_pTelecom = String.valueOf(pTelecom == 2 ? Defines.KOR_TELECOM_PACKET.KT_TELECOM : Defines.KOR_TELECOM_PACKET.LG_TELECOM).getBytes();
            System.arraycopy(byte_pTelecom, 0, packetBytes, offset8, byte_pTelecom.length);
            int offset9 = offset8 + 2;
            int offset10 = offset9 + 1;
            packetBytes[offset9] = (byte) Defines.IAP_SENDBPDATA_PROTOCOL.length();
            byte[] byte_pSendBPData = Defines.IAP_SENDBPDATA_PROTOCOL.getBytes();
            System.arraycopy(byte_pSendBPData, 0, packetBytes, offset10, byte_pSendBPData.length);
            int length = byte_pSendBPData.length + offset10;
        }
        packetBytes[packet_len3 - 1] = Defines.IAP_PARITY_BIT;
        byte ret = iapWriteBP(packetBytes);
        if (ret != 0) {
            init.setResultCode(ret);
            init.SetUserMessage(CommonString.ERROR_NETWORK_CONNECT_CHECK_STRING);
            return;
        }
        init.setResultCode((byte) 0);
    }

    protected static synchronized byte iapWriteBP(byte[] sendBuf) {
        byte b;
        synchronized (IAPNet.class) {
            if (!connectBP) {
                b = -2;
            } else {
                try {
                    outputStreamBP.write(sendBuf, 0, sendBuf.length);
                    outputStreamBP.flush();
                    b = 0;
                } catch (IOException e) {
                    CommonF.LOGGER.i(TAG, "[ DEBUG ] iapWriteBP() " + e);
                    b = -4;
                }
            }
        }
        return b;
    }

    protected static byte[] iapSendDataBP(byte[] data) {
        DataPacket dp = new DataPacket();
        CommonF.LOGGER.i(TAG, "[ DEBUG ] iapSendDataBP()");
        int packet_len = data.length + 12 + 1;
        byte[] packetBytes = new byte[packet_len];
        for (int i = 0; i < packet_len; i++) {
            packetBytes[i] = 0;
        }
        byte[] header = IAPNetworkUtil.iapMakePacketHeader("DP", data.length + 1);
        System.arraycopy(header, 0, packetBytes, 0, header.length);
        if (data != null) {
            System.arraycopy(data, 0, packetBytes, 12, data.length);
        }
        packetBytes[packet_len - 1] = Defines.IAP_PARITY_BIT;
        if (iapWriteBP(packetBytes) != 0) {
            return null;
        }
        byte[] recv = iapReceiveBP(dp);
        IAPNetworkUtil.packetDebug(recv, recv.length);
        if (dp.getResultCode() != 0) {
            return null;
        }
        dp.parse(recv);
        return dp.getData();
    }

    private static synchronized byte[] iapReceiveBP(Confirm c) {
        byte[] bArr;
        synchronized (IAPNet.class) {
            byte[] bArr2 = null;
            byte[] bArr3 = null;
            int receivedBytes = 0;
            try {
                byte[] header = new byte[12];
                while (true) {
                    if (receivedBytes >= 12) {
                        break;
                    }
                    int reads = inputStreamBP.read(header, receivedBytes, header.length - receivedBytes);
                    if (reads != -1) {
                        receivedBytes += reads;
                    } else if (reads == -1) {
                        c.setResultCode((byte) -5);
                        bArr = null;
                    }
                }
                CommonF.LOGGER.i(TAG, "[ DEBUG ] iapReceiveBP() Header Receive Complete!");
                String datalength = new String(header, 2, 10).trim();
                if (datalength.length() > 0) {
                    int data_len = Integer.parseInt(datalength);
                    CommonF.LOGGER.i(TAG, "[ DEBUG ] iapReceiveBP() Data Length: " + data_len);
                    byte[] data = new byte[data_len];
                    int receivedBytes2 = 0;
                    while (receivedBytes2 < data_len) {
                        int reads2 = inputStreamBP.read(data, receivedBytes2, data.length - receivedBytes2);
                        if (reads2 == -1) {
                            break;
                        }
                        receivedBytes2 += reads2;
                    }
                    byte b = data[data_len - 1];
                    byte[] returnData = new byte[(header.length + data.length)];
                    System.arraycopy(header, 0, returnData, 0, header.length);
                    System.arraycopy(data, 0, returnData, header.length, data.length - 1);
                    if (c != null) {
                        c.setResultCode((byte) 0);
                    }
                    IAPNetworkUtil.packetDebug(returnData, header.length + data.length);
                    bArr = returnData;
                } else {
                    c.setResultCode((byte) -5);
                    bArr = null;
                }
            } catch (Exception e) {
                CommonF.LOGGER.i(TAG, "[ Exception ] iapReceiveBP() " + e);
                c.setResultCode((byte) -5);
                bArr = null;
            }
        }
        return bArr;
    }

    protected static byte[] iapSendData(byte[] data) {
        DataPacket datapacket = new DataPacket();
        CommonF.LOGGER.e(TAG, "[ DEBUG ] iapSendData!!!!() Start ~~~~~~~~~~~~~~~~~~~~");
        int packet_len = data.length + 12 + 1;
        byte[] packetBytes = new byte[packet_len];
        for (int i = 0; i < packet_len; i++) {
            packetBytes[i] = 0;
        }
        byte[] header = IAPNetworkUtil.iapMakePacketHeader("DP", data.length + 1);
        System.arraycopy(header, 0, packetBytes, 0, header.length);
        if (data != null) {
            System.arraycopy(data, 0, packetBytes, 12, data.length);
        }
        packetBytes[packet_len - 1] = Defines.IAP_PARITY_BIT;
        if (iapWrite(packetBytes) != 0) {
            return null;
        }
        byte[] recv = iapReceive(datapacket);
        if (datapacket.getResultCode() != 0) {
            return null;
        }
        datapacket.parse(recv);
        CommonF.LOGGER.e(TAG, "[ DEBUG ] iapSendData() End~~~~~~~~~~~~~~~~~~~~");
        return datapacket.getData();
    }

    protected static boolean isConnect() {
        return connect;
    }

    protected static boolean isWifi() {
        return isWifi;
    }

    protected static void setWifi(boolean isWifi2) {
        isWifi = isWifi2;
    }

    /* JADX INFO: Multiple debug info for r0v12 int: [D('header' byte[]), D('offset' int)] */
    /* JADX INFO: Multiple debug info for r8v3 int: [D('byte_pID' byte[]), D('offset' int)] */
    /* JADX INFO: Multiple debug info for r8v10 byte[]: [D('recv' byte[]), D('ret' byte)] */
    protected static MsgConfirm iapSendItemQuery(String pID, String pName, String pTID, String pBPInfo) {
        String headerParam;
        int packet_len;
        String headerParam2 = "LP";
        MsgConfirm msgConfirm = new MsgConfirm();
        CommonF.LOGGER.i(TAG, "[ DEBUG ] iapSendItemQuery() PID=" + pID);
        int packet_len2 = 12 + 10 + 1;
        if (pName != null) {
            packet_len2 = pName.getBytes().length + 23;
            headerParam2 = "EP";
        }
        if (pTID != null) {
            int packet_len3 = packet_len2 + 100 + 1;
            headerParam = "NP";
            packet_len = packet_len3;
        } else {
            headerParam = headerParam2;
            packet_len = packet_len2;
        }
        if (pBPInfo != null) {
            packet_len += pBPInfo.getBytes().length;
        }
        int packet_len4 = packet_len + 1;
        byte[] packetBytes = new byte[packet_len4];
        for (int i = 0; i < packet_len4; i++) {
            packetBytes[i] = 0;
        }
        byte[] header = IAPNetworkUtil.iapMakePacketHeader(headerParam, packet_len4 - 12);
        IAPNetworkUtil.packetDebug(header, 12);
        System.arraycopy(header, 0, packetBytes, 0, header.length);
        int offset = 0 + 12;
        byte[] byte_pID = String.valueOf(pID).getBytes();
        System.arraycopy(byte_pID, 0, packetBytes, offset, byte_pID.length);
        int offset2 = offset + 10;
        if (pName != null) {
            packetBytes[offset2] = (byte) pName.getBytes().length;
        }
        int offset3 = offset2 + 1;
        if (pName != null) {
            System.arraycopy(pName.getBytes(), 0, packetBytes, offset3, pName.getBytes().length);
            offset3 = pName.getBytes().length + 23;
        }
        if (pTID != null) {
            System.arraycopy(pTID.getBytes(), 0, packetBytes, offset3, pTID.getBytes().length);
            offset3 = offset3 + 100 + 1;
        }
        if (pBPInfo != null) {
            packetBytes[offset3] = (byte) pBPInfo.getBytes().length;
            System.arraycopy(pBPInfo.getBytes(), 0, packetBytes, offset3, pBPInfo.getBytes().length);
            int offset4 = offset3 + pBPInfo.getBytes().length;
        }
        packetBytes[packet_len4 - 1] = Defines.IAP_PARITY_BIT;
        byte ret = iapWrite(packetBytes);
        if (ret != 0) {
            msgConfirm.setResultCode(ret);
            msgConfirm.SetUserMessage(CommonString.ERROR_NETWORK_SEND_RECV_CHECK_STRING);
            return msgConfirm;
        }
        byte[] recv = iapReceive(msgConfirm);
        if (msgConfirm.getResultCode() != 0) {
            msgConfirm.SetUserMessage(CommonString.ERROR_NETWORK_SEND_RECV_CHECK_STRING);
            return msgConfirm;
        }
        msgConfirm.parse(recv);
        return msgConfirm;
    }

    protected static ItemInfoConfirm iapSendItemInfoQuery(String pID) {
        ItemInfoConfirm itemInfoConfirm = new ItemInfoConfirm();
        CommonF.LOGGER.i(TAG, "[ DEBUG ] iapSendItemInfoQuery()");
        int packet_len = 12 + 10 + 1;
        byte[] packetBytes = new byte[packet_len];
        for (int i = 0; i < packet_len; i++) {
            packetBytes[i] = 0;
        }
        byte[] header = IAPNetworkUtil.iapMakePacketHeader("GP", packet_len - 12);
        System.arraycopy(header, 0, packetBytes, 0, header.length);
        byte[] byte_pID = String.valueOf(pID).getBytes();
        System.arraycopy(byte_pID, 0, packetBytes, 0 + 12, byte_pID.length);
        packetBytes[packet_len - 1] = Defines.IAP_PARITY_BIT;
        byte ret = iapWrite(packetBytes);
        if (ret != 0) {
            itemInfoConfirm.setResultCode(ret);
            itemInfoConfirm.SetUserMessage(CommonString.ERROR_NETWORK_SEND_RECV_CHECK_STRING);
        } else {
            byte[] recv = iapReceive(itemInfoConfirm);
            if (itemInfoConfirm.getResultCode() != 0) {
                itemInfoConfirm.SetUserMessage(CommonString.ERROR_NETWORK_SEND_RECV_CHECK_STRING);
            } else {
                itemInfoConfirm.parse(recv);
            }
        }
        return itemInfoConfirm;
    }

    /* JADX INFO: Multiple debug info for r0v17 int: [D('header' byte[]), D('offset' int)] */
    /* JADX INFO: Multiple debug info for r9v3 int: [D('byte_pID' byte[]), D('offset' int)] */
    /* JADX INFO: Multiple debug info for r9v7 byte[]: [D('recv' byte[]), D('ret' byte)] */
    protected static MsgConfirm iapSendItemPurchase(String pID, String pName, Boolean bTCash, String TID, String BPInfo, Boolean bUseBPProtocol) {
        String headerParam;
        int packet_len;
        MsgConfirm msgConfirm = new MsgConfirm();
        CommonF.LOGGER.i(TAG, "[ DEBUG ] iapSendItemPurchase() bUseBPProtocol = " + bUseBPProtocol + "  START !!!!!!!!");
        int packet_len2 = 12 + 10;
        if (bUseBPProtocol.booleanValue()) {
            headerParam = "BP";
            packet_len = packet_len2;
        } else {
            String headerParam2 = "HP";
            int packet_len3 = packet_len2 + 1 + 1;
            if (pName != null) {
                packet_len3 = pName.getBytes().length + 24;
            }
            if (TID != null) {
                headerParam2 = "OP";
                packet_len3 = packet_len3 + 100 + 1;
            }
            if (BPInfo != null) {
                int packet_len4 = packet_len3 + BPInfo.getBytes().length;
                headerParam = headerParam2;
                packet_len = packet_len4;
            } else {
                headerParam = headerParam2;
                packet_len = packet_len3;
            }
        }
        int packet_len5 = packet_len + 1;
        byte[] packetBytes = new byte[packet_len5];
        for (int i = 0; i < packet_len5; i++) {
            packetBytes[i] = 0;
        }
        CommonF.LOGGER.i(TAG, "[ DEBUG ] iapSendItemPurchase()  bTCash=" + bTCash + "// PName=" + (pName == null ? 0 : 1));
        byte[] header = IAPNetworkUtil.iapMakePacketHeader(headerParam, packet_len5 - 12);
        System.arraycopy(header, 0, packetBytes, 0, header.length);
        int offset = 0 + 12;
        byte[] byte_pID = String.valueOf(pID).getBytes();
        System.arraycopy(byte_pID, 0, packetBytes, offset, byte_pID.length);
        int offset2 = offset + 10;
        if (!bUseBPProtocol.booleanValue()) {
            if (bTCash.booleanValue()) {
                packetBytes[offset2] = 89;
            } else {
                packetBytes[offset2] = 78;
            }
            int offset3 = offset2 + 1;
            if (pName != null) {
                packetBytes[offset3] = (byte) pName.length();
            }
            int offset4 = offset3 + 1;
            if (pName != null) {
                System.arraycopy(pName.getBytes(), 0, packetBytes, offset4, pName.getBytes().length);
                offset4 = pName.getBytes().length + 24;
            }
            if (TID != null) {
                System.arraycopy(TID.getBytes(), 0, packetBytes, offset4, TID.getBytes().length);
                offset4 += 100;
            }
            if (BPInfo != null) {
                packetBytes[offset4] = (byte) BPInfo.length();
                int offset5 = offset4 + 1;
                System.arraycopy(BPInfo.getBytes(), 0, packetBytes, offset5, BPInfo.getBytes().length);
                int offset6 = offset5 + BPInfo.getBytes().length;
            }
        }
        packetBytes[packet_len5 - 1] = Defines.IAP_PARITY_BIT;
        byte ret = iapWrite(packetBytes);
        if (ret != 0) {
            msgConfirm.setResultCode(ret);
            msgConfirm.SetUserMessage(CommonString.ERROR_NETWORK_SEND_RECV_CHECK_STRING);
            return msgConfirm;
        }
        byte[] recv = iapReceive(msgConfirm);
        if (msgConfirm.getResultCode() != 0) {
            msgConfirm.SetUserMessage(CommonString.ERROR_NETWORK_SEND_RECV_CHECK_STRING);
            return msgConfirm;
        }
        msgConfirm.parse(recv);
        return msgConfirm;
    }

    /* JADX INFO: Multiple debug info for r2v19 int: [D('header' byte[]), D('offset' int)] */
    /* JADX INFO: Multiple debug info for r12v5 int: [D('byte_pCarrier' byte[]), D('offset' int)] */
    /* JADX INFO: Multiple debug info for r11v3 int: [D('byte_pMdn' byte[]), D('offset' int)] */
    /* JADX INFO: Multiple debug info for r11v17 byte[]: [D('recv' byte[]), D('ret' byte)] */
    protected static MsgConfirm iapSendItemPurchaseByDanal(String mdn, String pID, String pName, int pCarrier, String TID, String BPInfo, boolean bUseTCash, String encJumin) {
        int packet_len;
        boolean useXPProtocol;
        int packet_len2;
        String carrierStr;
        String headerParam = "KP";
        MsgConfirm itemPurchaseDanalCfm = new MsgConfirm();
        int packet_len3 = 12 + 1;
        if (pName != null) {
            packet_len = pName.length() + 13;
        } else {
            packet_len = packet_len3;
        }
        if (TID == null && BPInfo == null) {
            packet_len2 = packet_len;
            useXPProtocol = false;
        } else {
            int packet_len4 = packet_len + 100 + 1;
            if (BPInfo != null) {
                useXPProtocol = true;
                packet_len2 = packet_len4 + BPInfo.length();
                headerParam = "XP";
            } else {
                useXPProtocol = true;
                packet_len2 = packet_len4;
                headerParam = "XP";
            }
        }
        CommonF.LOGGER.i(TAG, "[ DEBUG ] iapSendItemPurchaseByDanal() STart!! " + headerParam);
        int packet_len5 = packet_len2 + 10 + 2 + 11 + 1 + encJumin.length() + 1 + 1;
        if (pCarrier == 2) {
            carrierStr = Defines.KOR_TELECOM_PACKET.KT_TELECOM;
        } else if (pCarrier == 3) {
            carrierStr = Defines.KOR_TELECOM_PACKET.LG_TELECOM;
        } else {
            carrierStr = Defines.KOR_TELECOM_PACKET.SK_TELECOM;
        }
        byte[] packetBytes = new byte[packet_len5];
        for (int i = 0; i < packet_len5; i++) {
            packetBytes[i] = 0;
        }
        byte[] header = IAPNetworkUtil.iapMakePacketHeader(headerParam, packet_len5 - 12);
        System.arraycopy(header, 0, packetBytes, 0, header.length);
        int offset = 0 + 12;
        byte[] byte_pID = String.valueOf(pID).getBytes();
        System.arraycopy(byte_pID, 0, packetBytes, offset, byte_pID.length);
        int offset2 = offset + 10;
        byte[] byte_pCarrier = String.valueOf(carrierStr).getBytes();
        System.arraycopy(byte_pCarrier, 0, packetBytes, offset2, byte_pCarrier.length);
        int offset3 = offset2 + 2;
        byte[] byte_pMdn = String.valueOf(mdn).getBytes();
        System.arraycopy(byte_pMdn, 0, packetBytes, offset3, byte_pMdn.length);
        int offset4 = offset3 + 11;
        if (TID != null) {
            System.arraycopy(TID.getBytes(), 0, packetBytes, offset4, TID.getBytes().length);
            offset4 += 100;
        }
        if (pName != null) {
            packetBytes[offset4] = (byte) pName.length();
        }
        int offset5 = offset4 + 1;
        if (pName != null) {
            System.arraycopy(pName.getBytes(), 0, packetBytes, offset5, pName.getBytes().length);
            offset5 += pName.getBytes().length;
        }
        if (useXPProtocol) {
            if (BPInfo != null) {
                packetBytes[offset5] = (byte) BPInfo.length();
            }
            offset5++;
            if (BPInfo != null) {
                System.arraycopy(BPInfo.getBytes(), 0, packetBytes, offset5, BPInfo.getBytes().length);
                offset5 += BPInfo.getBytes().length;
            }
        }
        int offset6 = offset5 + 1;
        packetBytes[offset5] = (byte) encJumin.length();
        byte[] byte_pJumin = String.valueOf(encJumin).getBytes();
        System.arraycopy(byte_pJumin, 0, packetBytes, offset6, byte_pJumin.length);
        int offset7 = encJumin.getBytes().length + offset6;
        CommonF.LOGGER.e("DEBUG", "Jumin=" + encJumin.length() + "  // Value = " + encJumin);
        if (bUseTCash) {
            int i2 = offset7 + 1;
            packetBytes[offset7] = 89;
        } else {
            int i3 = offset7 + 1;
            packetBytes[offset7] = 78;
        }
        packetBytes[packet_len5 - 1] = Defines.IAP_PARITY_BIT;
        byte ret = iapWrite(packetBytes);
        IAPNetworkUtil.packetDebug(packetBytes, packetBytes.length);
        if (ret != 0) {
            itemPurchaseDanalCfm.setResultCode(ret);
            itemPurchaseDanalCfm.SetUserMessage(CommonString.ERROR_NETWORK_SEND_RECV_CHECK_STRING);
            return itemPurchaseDanalCfm;
        }
        byte[] recv = iapReceive(itemPurchaseDanalCfm);
        if (itemPurchaseDanalCfm.getResultCode() != 0) {
            itemPurchaseDanalCfm.SetUserMessage(CommonString.ERROR_NETWORK_SEND_RECV_CHECK_STRING);
            return itemPurchaseDanalCfm;
        }
        itemPurchaseDanalCfm.parse(recv);
        CommonF.LOGGER.i(TAG, "[ DEBUG ] iapSendItemPurchaseByDanal()  End !!!!!!!!");
        return itemPurchaseDanalCfm;
    }

    protected static ItemWholeAuthConfirm iapSendItemWholeAuth() {
        ItemWholeAuthConfirm itemWholeAutchConfirm = new ItemWholeAuthConfirm();
        CommonF.LOGGER.i(TAG, "[ DEBUG ] iapSendItemWholeAuth()");
        int packet_len = 12 + 1;
        byte[] packetBytes = new byte[packet_len];
        for (int i = 0; i < packet_len; i++) {
            packetBytes[i] = 0;
        }
        byte[] header = IAPNetworkUtil.iapMakePacketHeader("AP", packet_len - 12);
        System.arraycopy(header, 0, packetBytes, 0, header.length);
        packetBytes[packet_len - 1] = Defines.IAP_PARITY_BIT;
        byte ret = iapWrite(packetBytes);
        if (ret != 0) {
            itemWholeAutchConfirm.setResultCode(ret);
            itemWholeAutchConfirm.SetUserMessage(CommonString.ERROR_NETWORK_SEND_RECV_CHECK_STRING);
        } else {
            byte[] recv = iapReceive(itemWholeAutchConfirm);
            if (itemWholeAutchConfirm.getResultCode() != 0) {
                itemWholeAutchConfirm.SetUserMessage(CommonString.ERROR_NETWORK_SEND_RECV_CHECK_STRING);
            } else {
                itemWholeAutchConfirm.parse(recv);
            }
        }
        return itemWholeAutchConfirm;
    }

    protected static ItemUseConfirm iapSendItemUse(String pID) {
        ItemUseConfirm itemUseConfirmMsg = new ItemUseConfirm();
        int packet_len = 12 + 10 + 1;
        byte[] packetBytes = new byte[packet_len];
        for (int i = 0; i < packet_len; i++) {
            packetBytes[i] = 0;
        }
        byte[] header = IAPNetworkUtil.iapMakePacketHeader("MP", packet_len - 12);
        System.arraycopy(header, 0, packetBytes, 0, header.length);
        byte[] byte_pID = String.valueOf(pID).getBytes();
        System.arraycopy(byte_pID, 0, packetBytes, 0 + 12, byte_pID.length);
        packetBytes[packet_len - 1] = Defines.IAP_PARITY_BIT;
        byte ret = iapWrite(packetBytes);
        if (ret != 0) {
            itemUseConfirmMsg.setResultCode(ret);
            itemUseConfirmMsg.SetUserMessage(CommonString.ERROR_NETWORK_SEND_RECV_CHECK_STRING);
        } else {
            byte[] recv = iapReceive(itemUseConfirmMsg);
            if (itemUseConfirmMsg.getResultCode() != 0) {
                itemUseConfirmMsg.SetUserMessage(CommonString.ERROR_NETWORK_SEND_RECV_CHECK_STRING);
            } else {
                itemUseConfirmMsg.parse(recv);
            }
        }
        return itemUseConfirmMsg;
    }
}
