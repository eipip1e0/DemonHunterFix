package com.lguplus.common.bill;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IBillSocket extends IInterface {
    void close() throws RemoteException;

    boolean connect(String str, String str2, int i) throws RemoteException;

    String getLastErrorMsg() throws RemoteException;

    int readByOffset(byte[] bArr, int i, int i2) throws RemoteException;

    int readBytes(byte[] bArr) throws RemoteException;

    boolean writeByOffset(byte[] bArr, int i, int i2) throws RemoteException;

    boolean writeBytes(byte[] bArr) throws RemoteException;

    public static abstract class Stub extends Binder implements IBillSocket {
        private static final String DESCRIPTOR = "com.lguplus.common.bill.IBillSocket";
        static final int TRANSACTION_close = 2;
        static final int TRANSACTION_connect = 1;
        static final int TRANSACTION_getLastErrorMsg = 7;
        static final int TRANSACTION_readByOffset = 4;
        static final int TRANSACTION_readBytes = 3;
        static final int TRANSACTION_writeByOffset = 6;
        static final int TRANSACTION_writeBytes = 5;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IBillSocket asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IBillSocket)) {
                return new Proxy(obj);
            }
            return (IBillSocket) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            int i;
            int i2;
            byte[] _arg0;
            byte[] _arg02;
            int i3;
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    boolean _result = connect(data.readString(), data.readString(), data.readInt());
                    reply.writeNoException();
                    if (_result) {
                        i3 = 1;
                    } else {
                        i3 = 0;
                    }
                    reply.writeInt(i3);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    close();
                    reply.writeNoException();
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0_length = data.readInt();
                    if (_arg0_length < 0) {
                        _arg02 = null;
                    } else {
                        _arg02 = new byte[_arg0_length];
                    }
                    int _result2 = readBytes(_arg02);
                    reply.writeNoException();
                    reply.writeInt(_result2);
                    reply.writeByteArray(_arg02);
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0_length2 = data.readInt();
                    if (_arg0_length2 < 0) {
                        _arg0 = null;
                    } else {
                        _arg0 = new byte[_arg0_length2];
                    }
                    int _result3 = readByOffset(_arg0, data.readInt(), data.readInt());
                    reply.writeNoException();
                    reply.writeInt(_result3);
                    reply.writeByteArray(_arg0);
                    return true;
                case 5:
                    data.enforceInterface(DESCRIPTOR);
                    boolean _result4 = writeBytes(data.createByteArray());
                    reply.writeNoException();
                    if (_result4) {
                        i2 = 1;
                    } else {
                        i2 = 0;
                    }
                    reply.writeInt(i2);
                    return true;
                case 6:
                    data.enforceInterface(DESCRIPTOR);
                    boolean _result5 = writeByOffset(data.createByteArray(), data.readInt(), data.readInt());
                    reply.writeNoException();
                    if (_result5) {
                        i = 1;
                    } else {
                        i = 0;
                    }
                    reply.writeInt(i);
                    return true;
                case 7:
                    data.enforceInterface(DESCRIPTOR);
                    String _result6 = getLastErrorMsg();
                    reply.writeNoException();
                    reply.writeString(_result6);
                    return true;
                case 1598968902:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IBillSocket {
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

            @Override // com.lguplus.common.bill.IBillSocket
            public boolean connect(String aid, String addr, int port) throws RemoteException {
                boolean _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(aid);
                    _data.writeString(addr);
                    _data.writeInt(port);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    } else {
                        _result = false;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.lguplus.common.bill.IBillSocket
            public void close() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.lguplus.common.bill.IBillSocket
            public int readBytes(byte[] b) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (b == null) {
                        _data.writeInt(-1);
                    } else {
                        _data.writeInt(b.length);
                    }
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.readByteArray(b);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.lguplus.common.bill.IBillSocket
            public int readByOffset(byte[] b, int offset, int length) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (b == null) {
                        _data.writeInt(-1);
                    } else {
                        _data.writeInt(b.length);
                    }
                    _data.writeInt(offset);
                    _data.writeInt(length);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.readByteArray(b);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.lguplus.common.bill.IBillSocket
            public boolean writeBytes(byte[] buffer) throws RemoteException {
                boolean _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByteArray(buffer);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    } else {
                        _result = false;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.lguplus.common.bill.IBillSocket
            public boolean writeByOffset(byte[] buffer, int offset, int length) throws RemoteException {
                boolean _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByteArray(buffer);
                    _data.writeInt(offset);
                    _data.writeInt(length);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    } else {
                        _result = false;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.lguplus.common.bill.IBillSocket
            public String getLastErrorMsg() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    return _reply.readString();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }
    }
}
