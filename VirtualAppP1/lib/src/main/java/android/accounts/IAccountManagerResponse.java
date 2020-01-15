//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package android.accounts;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IAccountManagerResponse extends IInterface {
    public void onError(int code, String message) throws RemoteException;

    public void onResult(Bundle bundle) throws RemoteException;

    public abstract static class Stub extends Binder implements IAccountManagerResponse {
        private static final String DESCRIPTOR = "android.accounts.IAccountManagerResponse";
        static final int TRANSACTION_onError = 2;
        static final int TRANSACTION_onResult = 1;

        protected Stub() {
            this.attachInterface(this, "android.accounts.IAccountManagerResponse");
        }

        protected static IAccountManagerResponse asInterface(IBinder var0) {
            if (var0 == null) {
                return null;
            } else {
                IInterface var1 = var0.queryLocalInterface("android.accounts.IAccountManagerResponse");
                return (IAccountManagerResponse)(var1 != null && var1 instanceof IAccountManagerResponse ? (IAccountManagerResponse)var1 : new IAccountManagerResponse.Stub.Proxy(var0));
            }
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException {
            if (var1 != 1598968902) {
                switch(var1) {
                    case 1:
                        var2.enforceInterface("android.accounts.IAccountManagerResponse");
                        Bundle var5;
                        if (var2.readInt() != 0) {
                            var5 = (Bundle)Bundle.CREATOR.createFromParcel(var2);
                        } else {
                            var5 = null;
                        }

                        this.onResult(var5);
                        return true;
                    case 2:
                        var2.enforceInterface("android.accounts.IAccountManagerResponse");
                        this.onError(var2.readInt(), var2.readString());
                        return true;
                    default:
                        return super.onTransact(var1, var2, var3, var4);
                }
            } else {
                var3.writeString("android.accounts.IAccountManagerResponse");
                return true;
            }
        }

        private static class Proxy implements IAccountManagerResponse {
            public IBinder mRemote;

            public Proxy(IBinder var1) {
                this.mRemote = var1;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public synchronized String getInterfaceDescriptor() {
                return "android.accounts.IAccountManagerResponse";
            }

            public synchronized void onError(int var1, String var2) throws RemoteException {
                Parcel var3 = Parcel.obtain();

                try {
                    var3.writeInterfaceToken("android.accounts.IAccountManagerResponse");
                    var3.writeInt(var1);
                    var3.writeString(var2);
                    this.mRemote.transact(2, var3, (Parcel)null, 1);
                } finally {
                    var3.recycle();
                }

            }

            public synchronized void onResult(Bundle var1) throws RemoteException {
                Parcel var2 = Parcel.obtain();

                label166: {
                    Throwable var10000;
                    label170: {
                        boolean var10001;
                        try {
                            var2.writeInterfaceToken("android.accounts.IAccountManagerResponse");
                        } catch (Throwable var22) {
                            var10000 = var22;
                            var10001 = false;
                            break label170;
                        }

                        if (var1 != null) {
                            try {
                                var2.writeInt(1);
                                var1.writeToParcel(var2, 0);
                            } catch (Throwable var21) {
                                var10000 = var21;
                                var10001 = false;
                                break label170;
                            }
                        } else {
                            try {
                                var2.writeInt(0);
                            } catch (Throwable var20) {
                                var10000 = var20;
                                var10001 = false;
                                break label170;
                            }
                        }

                        label156:
                        try {
                            this.mRemote.transact(1, var2, (Parcel)null, 1);
                            break label166;
                        } catch (Throwable var19) {
                            var10000 = var19;
                            var10001 = false;
                            break label156;
                        }
                    }

                    Throwable var23 = var10000;
                    var2.recycle();
//                    throw var23;
                }

                var2.recycle();
            }
        }
    }
}
