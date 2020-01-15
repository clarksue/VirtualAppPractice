package com.duapps.f.lib.client.ipc;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;

import com.duapps.f.lib.client.core.VirtualCore;
import com.duapps.f.lib.client.env.VirtualRuntime;
import com.duapps.f.lib.helper.compat.ActivityManagerCompat;
import com.duapps.f.lib.helper.ipcbus.IPCSingleton;
import com.duapps.f.lib.server.interfaces.IActivityManager;

public class VActivityManager {
    private static final VActivityManager sAM = new VActivityManager();
    private IPCSingleton<IActivityManager> singleton = new IPCSingleton<>(IActivityManager.class);

    public static VActivityManager get() {
        return sAM;
    }

    public IActivityManager getService() {
        return singleton.get();
    }

    public int startActivity(Intent intent, ActivityInfo info, IBinder resultTo, Bundle options, String resultWho, int requestCode, int userId) {
        try {
            return getService().startActivity(intent, info, resultTo, options, resultWho, requestCode, userId);
        } catch (RemoteException e) {
            return VirtualRuntime.crash(e);
        }
    }

    public boolean isAppProcess(String processName) {
        try {
            return getService().isAppProcess(processName);
        } catch (RemoteException e) {
            return VirtualRuntime.crash(e);
        }
    }

    public int startActivity(Intent intent, int userId) {
        if (userId < 0) {
            return ActivityManagerCompat.START_NOT_CURRENT_USER_ACTIVITY;
        }
        ActivityInfo info = VirtualCore.get().resolveActivityInfo(intent, userId);
        if (info == null) {
            return ActivityManagerCompat.START_INTENT_NOT_RESOLVED;
        }
        return startActivity(intent, info, null, null, null, 0, userId);
    }

    public int getUidByPid(int pid) {
        try {
            return getService().getUidByPid(pid);
        } catch (RemoteException e) {
            return VirtualRuntime.crash(e);
        }
    }

    public int getSystemPid() {
        try {
            return getService().getSystemPid();
        } catch (RemoteException e) {
            return VirtualRuntime.crash(e);
        }
    }
}
