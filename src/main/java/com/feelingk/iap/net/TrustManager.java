package com.feelingk.iap.net;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.X509TrustManager;

public class TrustManager implements X509TrustManager {
    @Override // javax.net.ssl.X509TrustManager
    public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
    }

    @Override // javax.net.ssl.X509TrustManager
    public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
    }

    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }
}
