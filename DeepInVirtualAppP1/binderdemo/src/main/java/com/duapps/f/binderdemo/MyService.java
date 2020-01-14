package com.duapps.f.binderdemo;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserHandle;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public synchronized ComponentName startForegroundServiceAsUser(Intent service, UserHandle user) {
        return null;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    IMyAidlInterface.Stub mBinder = new IMyAidlInterface.Stub() {
        @Override
        public String convert(String aString) throws RemoteException {
            return "=============" + aString + "=============";
        }
    };
}
