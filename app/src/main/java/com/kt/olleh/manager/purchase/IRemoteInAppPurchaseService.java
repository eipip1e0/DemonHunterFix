package com.kt.olleh.manager.purchase;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.Map;

public interface IRemoteInAppPurchaseService extends IInterface {
    Map cancel(String str, String str2, String str3) throws RemoteException;

    Map purchase(String str, String str2, String str3) throws RemoteException;

    public static abstract class Stub extends Binder implements IRemoteInAppPurchaseService {
        private static final String DESCRIPTOR = "com.kt.olleh.manager.purchase.IRemoteInAppPurchaseService";
        static final int TRANSACTION_cancel = 2;
        static final int TRANSACTION_purchase = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IRemoteInAppPurchaseService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IRemoteInAppPurchaseService)) {
                return new Proxy(obj);
            }
            return (IRemoteInAppPurchaseService) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    Map _result = purchase(data.readString(), data.readString(), data.readString());
                    reply.writeNoException();
                    reply.writeMap(_result);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    Map _result2 = cancel(data.readString(), data.readString(), data.readString());
                    reply.writeNoException();
                    reply.writeMap(_result2);
                    return true;
                case 1598968902:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IRemoteInAppPurchaseService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // com.kt.olleh.manager.purchase.IRemoteInAppPurchaseService
            public Map purchase(String packageName, String iv, String encryptItemId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(iv);
                    _data.writeString(encryptItemId);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    return _reply.readHashMap(getClass().getClassLoader());
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.kt.olleh.manager.purchase.IRemoteInAppPurchaseService
            public Map cancel(String packageName, String iv, String encryptItemId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(iv);
                    _data.writeString(encryptItemId);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    return _reply.readHashMap(getClass().getClassLoader());
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }
    }
}
