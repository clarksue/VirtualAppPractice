// IUiCallback.aidl
package com.duapps.f.lib.server.interfaces;

interface IUiCallback {
    void onAppOpened(in String packageName, in int userId);
}
