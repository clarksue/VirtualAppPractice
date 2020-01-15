package com.duapps.f.lib.helper.ipcbus;

import android.os.IBinder;

public interface IServerCache {
    void join(String serverName, IBinder binder);
    IBinder query(String serverName);
}
