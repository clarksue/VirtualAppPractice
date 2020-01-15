package com.duapps.f.lib.server.interfaces;

import android.os.RemoteException;

import com.duapps.f.lib.remote.VDeviceInfo;

/**
 * @author Lody
 */
public interface IDeviceInfoManager {

    VDeviceInfo getDeviceInfo(int userId) throws RemoteException;

    void updateDeviceInfo(int userId, VDeviceInfo info) throws RemoteException;

}
