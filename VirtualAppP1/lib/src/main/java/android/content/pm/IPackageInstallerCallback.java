package android.content.pm;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IPackageInstallerCallback extends IInterface {
    void onSessionActiveChanged(int var1, boolean var2) throws RemoteException;

    void onSessionBadgingChanged(int var1) throws RemoteException;

    public void onSessionCreated(int var1) throws RemoteException;

    void onSessionFinished(int var1, boolean var2) throws RemoteException;

    void onSessionProgressChanged(int var1, float var2) throws RemoteException;

    public abstract static class Stub extends Binder implements IPackageInstallerCallback {
        private static final String DESCRIPTOR = "android.content.pm.IPackageInstallerCallback";
        static final int TRANSACTION_onSessionActiveChanged = 3;
        static final int TRANSACTION_onSessionBadgingChanged = 2;
        static final int TRANSACTION_onSessionCreated = 1;
        static final int TRANSACTION_onSessionFinished = 5;
        static final int TRANSACTION_onSessionProgressChanged = 4;

        public Stub() {
            this.attachInterface(this, "android.content.pm.IPackageInstallerCallback");
        }

        private static IPackageInstallerCallback asInterface(IBinder var0) {
            if (var0 == null) {
                return null;
            } else {
                IInterface var1 = var0.queryLocalInterface("android.content.pm.IPackageInstallerCallback");
                return (IPackageInstallerCallback)(var1 != null && var1 instanceof IPackageInstallerCallback ? (IPackageInstallerCallback)var1 : new IPackageInstallerCallback.Stub.Proxy(var0));
            }
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException {
            if (var1 != 1598968902) {
                boolean var5 = false;
                boolean var6 = false;
                switch(var1) {
                    case 1:
                        var2.enforceInterface("android.content.pm.IPackageInstallerCallback");
                        this.onSessionCreated(var2.readInt());
                        return true;
                    case 2:
                        var2.enforceInterface("android.content.pm.IPackageInstallerCallback");
                        this.onSessionBadgingChanged(var2.readInt());
                        return true;
                    case 3:
                        var2.enforceInterface("android.content.pm.IPackageInstallerCallback");
                        var1 = var2.readInt();
                        var6 = var5;
                        if (var2.readInt() != 0) {
                            var6 = true;
                        }

                        this.onSessionActiveChanged(var1, var6);
                        return true;
                    case 4:
                        var2.enforceInterface("android.content.pm.IPackageInstallerCallback");
                        this.onSessionProgressChanged(var2.readInt(), var2.readFloat());
                        return true;
                    case 5:
                        var2.enforceInterface("android.content.pm.IPackageInstallerCallback");
                        var1 = var2.readInt();
                        if (var2.readInt() != 0) {
                            var6 = true;
                        }

                        this.onSessionFinished(var1, var6);
                        return true;
                    default:
                        return super.onTransact(var1, var2, var3, var4);
                }
            } else {
                var3.writeString("android.content.pm.IPackageInstallerCallback");
                return true;
            }
        }

        private static class Proxy implements IPackageInstallerCallback {
            public IBinder mRemote;

            public Proxy(IBinder var1) {
                this.mRemote = var1;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public synchronized String getInterfaceDescriptor() {
                return "android.content.pm.IPackageInstallerCallback";
            }

            public synchronized void onSessionActiveChanged(int var1, boolean var2) throws RemoteException {
                Parcel var3 = Parcel.obtain();

                try {
                    var3.writeInterfaceToken("android.content.pm.IPackageInstallerCallback");
                    var3.writeInt(var1);
                    var3.writeInt(var2?1:0);
                    this.mRemote.transact(3, var3, (Parcel)null, 1);
                } finally {
                    var3.recycle();
                }

            }

            public synchronized void onSessionBadgingChanged(int var1) throws RemoteException {
                Parcel var2 = Parcel.obtain();

                try {
                    var2.writeInterfaceToken("android.content.pm.IPackageInstallerCallback");
                    var2.writeInt(var1);
                    this.mRemote.transact(2, var2, (Parcel)null, 1);
                } finally {
                    var2.recycle();
                }

            }

            public synchronized void onSessionCreated(int var1) throws RemoteException {
                Parcel var2 = Parcel.obtain();

                try {
                    var2.writeInterfaceToken("android.content.pm.IPackageInstallerCallback");
                    var2.writeInt(var1);
                    this.mRemote.transact(1, var2, (Parcel)null, 1);
                } finally {
                    var2.recycle();
                }

            }

            public synchronized void onSessionFinished(int var1, boolean var2) throws RemoteException {
                Parcel var3 = Parcel.obtain();

                try {
                    var3.writeInterfaceToken("android.content.pm.IPackageInstallerCallback");
                    var3.writeInt(var1);
                    var3.writeInt(var2 ? 1 : 0);
                    this.mRemote.transact(5, var3, (Parcel)null, 1);
                } finally {
                    var3.recycle();
                }

            }

            public synchronized void onSessionProgressChanged(int var1, float var2) throws RemoteException {
                Parcel var3 = Parcel.obtain();

                try {
                    var3.writeInterfaceToken("android.content.pm.IPackageInstallerCallback");
                    var3.writeInt(var1);
                    var3.writeFloat(var2);
                    this.mRemote.transact(4, var3, (Parcel)null, 1);
                } finally {
                    var3.recycle();
                }

            }
        }
    }
}
