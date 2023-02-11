package com.bicore;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import com.lguplus.common.bill.IBillSocket;

public class LGUMarket extends BicoreActivity {
    private ServiceConnection serviceConn = new ServiceConnection() {
        /* class com.bicore.LGUMarket.AnonymousClass1 */

        public void onServiceConnected(ComponentName arg0, IBinder arg1) {
            IBillSocket billSock = IBillSocket.Stub.asInterface(arg1);
            try {
                billSock.connect(LGUMarket.this.DataFolder, LGUMarket.this.DataFolder, LGUMarket.this.ArmResult);
                billSock.close();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        public void onServiceDisconnected(ComponentName arg0) {
        }
    };

    @Override // com.bicore.BicoreActivity, com.feelingk.iap.IAPActivity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindService(new Intent(IBillSocket.class.getName()), this.serviceConn, 1);
    }

    /* access modifiers changed from: protected */
    @Override // com.bicore.BicoreActivity
    public void onDestroy() {
        super.onDestroy();
        unbindService(this.serviceConn);
    }
}
