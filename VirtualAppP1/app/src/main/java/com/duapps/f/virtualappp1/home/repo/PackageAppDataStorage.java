package com.duapps.f.virtualappp1.home.repo;

import com.duapps.f.lib.client.core.VirtualCore;
import com.duapps.f.lib.remote.InstalledAppInfo;
import com.duapps.f.virtualappp1.VApp;
import com.duapps.f.virtualappp1.abs.Callback;
import com.duapps.f.virtualappp1.abs.ui.VUiKit;
import com.duapps.f.virtualappp1.home.models.PackageAppData;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Lody
 *         <p>
 *         Cache the loaded PackageAppData.
 */
public class PackageAppDataStorage {

    private static final PackageAppDataStorage STORAGE = new PackageAppDataStorage();
    private final Map<String, PackageAppData> packageDataMap = new HashMap<>();

    public static PackageAppDataStorage get() {
        return STORAGE;
    }

    public PackageAppData acquire(String packageName) {
        PackageAppData data;
        synchronized (packageDataMap) {
            data = packageDataMap.get(packageName);
            if (data == null) {
                data = loadAppData(packageName);
            }
        }
        return data;
    }

    public void acquire(String packageName, Callback<PackageAppData> callback) {
        VUiKit.defer()
                .when(() -> acquire(packageName))
                .done(callback::callback);
    }

    private PackageAppData loadAppData(String packageName) {
        InstalledAppInfo setting = VirtualCore.get().getInstalledAppInfo(packageName, 0);
        if (setting != null) {
            PackageAppData data = new PackageAppData(VApp.getApp(), setting);
            synchronized (packageDataMap) {
                packageDataMap.put(packageName, data);
            }
            return data;
        }
        return null;
    }

}
