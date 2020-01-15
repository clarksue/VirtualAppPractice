package com.duapps.f.lib.client;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ProviderInfo;
import android.os.ConditionVariable;
import android.os.IBinder;
import android.os.RemoteException;

public final class VClientImpl extends IVClient.Stub {

    private static final int NEW_INTENT = 11;
    private static final int RECEIVER = 12;

    private static final String TAG = VClientImpl.class.getSimpleName();
    @SuppressLint("StaticFieldLeak")
    private static final VClientImpl gClient = new VClientImpl();
    private ConditionVariable mTempLock;
    private IBinder token;
    private int vuid;
    private Application mInitialApplication;

    public static VClientImpl get() {
        return gClient;
    }

    public void initProcess(IBinder token, int vuid) {
        this.token = token;
        this.vuid = vuid;
    }

    public int getVUid() {
        return vuid;
    }

    @Override
    public void scheduleNewIntent(String creator, IBinder token, Intent intent) throws RemoteException {

    }

    @Override
    public void finishActivity(IBinder token) throws RemoteException {

    }

    @Override
    public IBinder createProxyService(ComponentName component, IBinder binder) throws RemoteException {
        return null;
    }

    @Override
    public IBinder acquireProviderClient(ProviderInfo info) throws RemoteException {
        return null;
    }

    @Override
    public IBinder getAppThread() throws RemoteException {
        return null;
    }

    @Override
    public IBinder getToken() throws RemoteException {
        return null;
    }

    @Override
    public String getDebugInfo() throws RemoteException {
        return null;
    }
}
