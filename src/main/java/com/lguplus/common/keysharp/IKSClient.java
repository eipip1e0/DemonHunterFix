package com.lguplus.common.keysharp;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IKSClient extends IInterface {
    byte[] decryptMessage(byte[] bArr) throws RemoteException;

    byte[] decryptMessageBase64(String str) throws RemoteException;

    byte[] encryptMessage(byte[] bArr) throws RemoteException;

    String encryptMessageBase64(byte[] bArr) throws RemoteException;

    int finalizeKey(byte[] bArr) throws RemoteException;

    int finalizeKeyValidity(byte[] bArr, int i) throws RemoteException;

    byte[] initializeKey() throws RemoteException;

    public static abstract class Stub extends Binder implements IKSClient {
        private static final String DESCRIPTOR = "com.lguplus.common.keysharp.IKSClient";
        static final int TRANSACTION_decryptMessage = 6;
        static final int TRANSACTION_decryptMessageBase64 = 7;
        static final int TRANSACTION_encryptMessage = 4;
        static final int TRANSACTION_encryptMessageBase64 = 5;
        static final int TRANSACTION_finalizeKey = 2;
        static final int TRANSACTION_finalizeKeyValidity = 3;
        static final int TRANSACTION_initializeKey = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IKSClient asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IKSClient)) {
                return new Proxy(obj);
            }
            return (IKSClient) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    byte[] _result = initializeKey();
                    reply.writeNoException();
                    reply.writeByteArray(_result);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    int _result2 = finalizeKey(data.createByteArray());
                    reply.writeNoException();
                    reply.writeInt(_result2);
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    int _result3 = finalizeKeyValidity(data.createByteArray(), data.readInt());
                    reply.writeNoException();
                    reply.writeInt(_result3);
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    byte[] _result4 = encryptMessage(data.createByteArray());
                    reply.writeNoException();
                    reply.writeByteArray(_result4);
                    return true;
                case 5:
                    data.enforceInterface(DESCRIPTOR);
                    String _result5 = encryptMessageBase64(data.createByteArray());
                    reply.writeNoException();
                    reply.writeString(_result5);
                    return true;
                case 6:
                    data.enforceInterface(DESCRIPTOR);
                    byte[] _result6 = decryptMessage(data.createByteArray());
                    reply.writeNoException();
                    reply.writeByteArray(_result6);
                    return true;
                case 7:
                    data.enforceInterface(DESCRIPTOR);
                    byte[] _result7 = decryptMessageBase64(data.readString());
                    reply.writeNoException();
                    reply.writeByteArray(_result7);
                    return true;
                case 1598968902:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IKSClient {
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

            @Override // com.lguplus.common.keysharp.IKSClient
            public byte[] initializeKey() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    return _reply.createByteArray();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.lguplus.common.keysharp.IKSClient
            public int finalizeKey(byte[] input) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByteArray(input);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    return _reply.readInt();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.lguplus.common.keysharp.IKSClient
            public int finalizeKeyValidity(byte[] input, int flag) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByteArray(input);
                    _data.writeInt(flag);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    return _reply.readInt();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.lguplus.common.keysharp.IKSClient
            public byte[] encryptMessage(byte[] plain) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByteArray(plain);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    return _reply.createByteArray();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.lguplus.common.keysharp.IKSClient
            public String encryptMessageBase64(byte[] plain) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByteArray(plain);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    return _reply.readString();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.lguplus.common.keysharp.IKSClient
            public byte[] decryptMessage(byte[] cipher) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByteArray(cipher);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    return _reply.createByteArray();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.lguplus.common.keysharp.IKSClient
            public byte[] decryptMessageBase64(String cipher) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(cipher);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    return _reply.createByteArray();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }
    }
}
