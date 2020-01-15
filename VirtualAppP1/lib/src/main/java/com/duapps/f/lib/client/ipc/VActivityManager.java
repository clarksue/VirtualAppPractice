package com.duapps.f.lib.client.ipc;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;

import com.duapps.f.lib.client.core.VirtualCore;
import com.duapps.f.lib.client.env.VirtualRuntime;
import com.duapps.f.lib.helper.compat.ActivityManagerCompat;
import com.duapps.f.lib.helper.ipcbus.IPCSingleton;
import com.duapps.f.lib.server.interfaces.IActivityManager;

import java.util.HashMap;
import java.util.Map;

public class VActivityManager {
    private static final VActivityManager sAM = new VActivityManager();
    private final Map<IBinder, ActivityClientRecord> mActivities = new HashMap<IBinder, ActivityClientRecord>(6);
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

    public ComponentName startService(IInterface caller, Intent service, String resolvedType, int userId) {
        try {
            return getService().startService(caller != null ? caller.asBinder() : null, service, resolvedType, userId);
        } catch (RemoteException e) {
            return VirtualRuntime.crash(e);
        }
    }

    public ActivityClientRecord onActivityCreate(ComponentName component, ComponentName caller, IBinder token, ActivityInfo info, Intent intent, String affinity, int taskId, int launchMode, int flags) {
        ActivityClientRecord r = new ActivityClientRecord();
        r.info = info;
        mActivities.put(token, r);
        try {
            getService().onActivityCreated(component, caller, token, intent, affinity, taskId, launchMode, flags);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return r;
    }

    public ActivityClientRecord getActivityRecord(IBinder token) {
        synchronized (mActivities) {
            return token == null ? null : mActivities.get(token);
        }
    }

    public void appDoneExecuting() {
        try {
            getService().appDoneExecuting();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void processRestarted(String packageName, String processName, int userId) {
        try {
            getService().processRestarted(packageName, processName, userId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
