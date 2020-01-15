package com.duapps.f.lib.os;

import android.os.Binder;

import com.duapps.f.lib.client.ipc.VActivityManager;

public class VBinder {
    public static int getCallingUid() {
        return VActivityManager.get().getUidByPid(Binder.getCallingPid());
    }
}
