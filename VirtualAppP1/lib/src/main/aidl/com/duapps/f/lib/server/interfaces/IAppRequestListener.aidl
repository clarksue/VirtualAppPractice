// IAppRequestListener.aidl
package com.duapps.f.lib.server.interfaces;

// Declare any non-default types here with import statements

interface IAppRequestListener {
    void onRequestInstall(in String path);
    void onRequestUninstall(in String pkg);
}
