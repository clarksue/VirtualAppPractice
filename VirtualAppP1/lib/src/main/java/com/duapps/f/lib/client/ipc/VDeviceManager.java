package com.duapps.f.lib.client.ipc;

import android.os.RemoteException;

import com.duapps.f.lib.client.env.VirtualRuntime;
import com.duapps.f.lib.helper.ipcbus.IPCSingleton;
import com.duapps.f.lib.remote.VDeviceInfo;
import com.duapps.f.lib.server.interfaces.IDeviceInfoManager;

/**
 * @author Lody
 */

public class VDeviceManager {

    private static final VDeviceManager sInstance = new VDeviceManager();
    private IPCSingleton<IDeviceInfoManager> singleton = new IPCSingleton<>(IDeviceInfoManager.class);


    public static VDeviceManager get() {
        return sInstance;
    }


    public IDeviceInfoManager getService() {
        return singleton.get();
    }

    public VDeviceInfo getDeviceInfo(int userId) {
        try {
            return getService().getDeviceInfo(userId);
        } catch (RemoteException e) {
            return VirtualRuntime.crash(e);
        }
    }
}