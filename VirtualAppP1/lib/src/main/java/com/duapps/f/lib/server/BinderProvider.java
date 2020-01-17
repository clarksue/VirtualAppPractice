package com.duapps.f.lib.server;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;

import com.duapps.f.lib.client.core.VirtualCore;
import com.duapps.f.lib.client.stub.DaemonService;
import com.duapps.f.lib.helper.compat.BundleCompat;
import com.duapps.f.lib.helper.ipcbus.IPCBus;
import com.duapps.f.lib.server.accounts.VAccountManagerService;
import com.duapps.f.lib.server.am.BroadcastSystem;
import com.duapps.f.lib.server.am.VActivityManagerService;
import com.duapps.f.lib.server.device.VDeviceManagerService;
import com.duapps.f.lib.server.interfaces.IAccountManager;
import com.duapps.f.lib.server.interfaces.IActivityManager;
import com.duapps.f.lib.server.interfaces.IAppManager;
import com.duapps.f.lib.server.interfaces.IDeviceInfoManager;
import com.duapps.f.lib.server.interfaces.IJobService;
import com.duapps.f.lib.server.interfaces.INotificationManager;
import com.duapps.f.lib.server.interfaces.IPackageManager;
import com.duapps.f.lib.server.interfaces.IServiceFetcher;
import com.duapps.f.lib.server.interfaces.IUserManager;
import com.duapps.f.lib.server.interfaces.IVirtualLocationManager;
import com.duapps.f.lib.server.interfaces.IVirtualStorageService;
import com.duapps.f.lib.server.job.VJobSchedulerService;
import com.duapps.f.lib.server.location.VirtualLocationService;
import com.duapps.f.lib.server.notification.VNotificationManagerService;
import com.duapps.f.lib.server.pm.VAppManagerService;
import com.duapps.f.lib.server.pm.VPackageManagerService;
import com.duapps.f.lib.server.pm.VUserManagerService;
import com.duapps.f.lib.server.vs.VirtualStorageService;

/**
 * @author Lody
 */
public final class BinderProvider extends ContentProvider {

    private final ServiceFetcher mServiceFetcher = new ServiceFetcher();

    @Override
    public boolean onCreate() {
        Context context = getContext();
        DaemonService.startup(context);
        if (!VirtualCore.get().isStartup()) {
            return true;
        }
        VPackageManagerService.systemReady();
        IPCBus.register(IPackageManager.class, VPackageManagerService.get());
        VActivityManagerService.systemReady(context);
        IPCBus.register(IActivityManager.class, VActivityManagerService.get());
        IPCBus.register(IUserManager.class, VUserManagerService.get());
        VAppManagerService.systemReady();
        IPCBus.register(IAppManager.class, VAppManagerService.get());
        BroadcastSystem.attach(VActivityManagerService.get(), VAppManagerService.get());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            IPCBus.register(IJobService.class, VJobSchedulerService.get());
        }
        VNotificationManagerService.systemReady(context);
        IPCBus.register(INotificationManager.class, VNotificationManagerService.get());
        VAppManagerService.get().scanApps();
        VAccountManagerService.systemReady();
        IPCBus.register(IAccountManager.class, VAccountManagerService.get());
        IPCBus.register(IVirtualStorageService.class, VirtualStorageService.get());
        IPCBus.register(IDeviceInfoManager.class, VDeviceManagerService.get());
        IPCBus.register(IVirtualLocationManager.class, VirtualLocationService.get());
        return true;
    }

    @Override
    public Bundle call(String method, String arg, Bundle extras) {
        if ("@".equals(method)) {
            Bundle bundle = new Bundle();
            BundleCompat.putBinder(bundle, "_VA_|_binder_", mServiceFetcher);
            return bundle;
        }
        if ("register".equals(method)) {

        }
        return null;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    private class ServiceFetcher extends IServiceFetcher.Stub {
        @Override
        public IBinder getService(String name) throws RemoteException {
            if (name != null) {
                return ServiceCache.getService(name);
            }
            return null;
        }

        @Override
        public void addService(String name, IBinder service) throws RemoteException {
            if (name != null && service != null) {
                ServiceCache.addService(name, service);
            }
        }

        @Override
        public void removeService(String name) throws RemoteException {
            if (name != null) {
                ServiceCache.removeService(name);
            }
        }
    }
}
