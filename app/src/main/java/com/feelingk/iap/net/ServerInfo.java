package com.feelingk.iap.net;

public class ServerInfo {
    private final int DEFAULT_PORT = -1;
    private String host = null;
    private int port = -1;

    public ServerInfo(String host2) {
        this.host = host2;
        this.port = -1;
    }

    public ServerInfo(String host2, int port2) {
        this.host = host2;
        this.port = port2;
    }

    public String getHostAddress() {
        return this.host;
    }

    public int getPort() {
        return this.port;
    }

    public String toString() {
        return "Host: " + this.host + ", Port: " + this.port;
    }
}
