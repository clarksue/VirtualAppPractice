// IAppRequestListener.aidl
package com.duapps.f.lib.server.interfaces;

interface IAppRequestListener {
    void onRequestInstall(in String path);
    void onRequestUninstall(in String pkg);
}
