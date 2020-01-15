//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package android.content;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IIntentReceiver extends IInterface {
    void performReceive(Intent var1, int var2, String var3, Bundle var4, boolean var5, boolean var6, int var7) throws RemoteException;

    public abstract static class Stub extends Binder implements IIntentReceiver {
        private static final String DESCRIPTOR = "android.content.IIntentReceiver";
        static final int TRANSACTION_performReceive = 1;

        protected Stub() {
            this.attachInterface(this, "android.content.IIntentReceiver");
        }

        protected static IIntentReceiver asInterface(IBinder var0) {
            if (var0 == null) {
                return null;
            } else {
                IInterface var1 = var0.queryLocalInterface("android.content.IIntentReceiver");
                return (IIntentReceiver)(var1 != null && var1 instanceof IIntentReceiver ? (IIntentReceiver)var1 : new IIntentReceiver.Stub.Proxy(var0));
            }
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException {
            if (var1 != 1) {
                if (var1 != 1598968902) {
                    return super.onTransact(var1, var2, var3, var4);
                } else {
                    var3.writeString("android.content.IIntentReceiver");
                    return true;
                }
            } else {
                var2.enforceInterface("android.content.IIntentReceiver");
                var1 = var2.readInt();
                Bundle var5 = null;
                Intent var9;
                if (var1 != 0) {
                    var9 = (Intent)Intent.CREATOR.createFromParcel(var2);
                } else {
                    var9 = null;
                }

                var1 = var2.readInt();
                String var6 = var2.readString();
                if (var2.readInt() != 0) {
                    var5 = (Bundle)Bundle.CREATOR.createFromParcel(var2);
                }

                boolean var7;
                if (var2.readInt() != 0) {
                    var7 = true;
                } else {
                    var7 = false;
                }

                boolean var8;
                if (var2.readInt() != 0) {
                    var8 = true;
                } else {
                    var8 = false;
                }

                this.performReceive(var9, var1, var6, var5, var7, var8, var2.readInt());
                return true;
            }
        }

        private static class Proxy implements IIntentReceiver {
            public IBinder mRemote;

            public Proxy(IBinder var1) {
                this.mRemote = var1;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public synchronized String getInterfaceDescriptor() {
                return "android.content.IIntentReceiver";
            }

            public synchronized void performReceive(Intent var1, int var2, String var3, Bundle var4, boolean var5, boolean var6, int var7) throws RemoteException {
                Parcel var8 = Parcel.obtain();

                label404: {
                    Throwable var10000;
                    label408: {
                        boolean var10001;
                        try {
                            var8.writeInterfaceToken("android.content.IIntentReceiver");
                        } catch (Throwable var64) {
                            var10000 = var64;
                            var10001 = false;
                            break label408;
                        }

                        if (var1 != null) {
                            try {
                                var8.writeInt(1);
                                var1.writeToParcel(var8, 0);
                            } catch (Throwable var63) {
                                var10000 = var63;
                                var10001 = false;
                                break label408;
                            }
                        } else {
                            try {
                                var8.writeInt(0);
                            } catch (Throwable var62) {
                                var10000 = var62;
                                var10001 = false;
                                break label408;
                            }
                        }

                        try {
                            var8.writeInt(var2);
                            var8.writeString(var3);
                        } catch (Throwable var61) {
                            var10000 = var61;
                            var10001 = false;
                            break label408;
                        }

                        if (var4 != null) {
                            try {
                                var8.writeInt(1);
                                var4.writeToParcel(var8, 0);
                            } catch (Throwable var60) {
                                var10000 = var60;
                                var10001 = false;
                                break label408;
                            }
                        } else {
                            try {
                                var8.writeInt(0);
                            } catch (Throwable var59) {
                                var10000 = var59;
                                var10001 = false;
                                break label408;
                            }
                        }

                        label385:
                        try {
//                            var8.writeInt(var5);
                            var8.writeBoolean(var5);
                            var8.writeBoolean(var6);
                            var8.writeInt(var7);
                            this.mRemote.transact(1, var8, (Parcel)null, 1);
                            break label404;
                        } catch (Throwable var58) {
                            var10000 = var58;
                            var10001 = false;
                            break label385;
                        }
                    }

                    Throwable var65 = var10000;
                    var8.recycle();
//                    throw var65;
                }

                var8.recycle();
            }
        }
    }
}
