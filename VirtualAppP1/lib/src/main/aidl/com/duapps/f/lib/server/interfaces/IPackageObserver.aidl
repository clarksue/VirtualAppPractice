// IPackageObserver.aidl
package com.duapps.f.lib.server.interfaces;

interface IPackageObserver {
    void onPackageInstalled(in String packageName);
    void onPackageUninstalled(in String packageName);
    void onPackageInstalledAsUser(in int userId, in String packageName);
    void onPackageUninstalledAsUser(in int userId, in String packageName);
}
