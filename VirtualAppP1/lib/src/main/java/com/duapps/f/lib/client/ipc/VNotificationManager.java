package com.duapps.f.lib.client.ipc;

import android.app.Notification;
import android.os.RemoteException;

import com.duapps.f.lib.client.core.VirtualCore;
import com.duapps.f.lib.helper.ipcbus.IPCSingleton;
import com.duapps.f.lib.server.interfaces.INotificationManager;
import com.duapps.f.lib.server.notification.NotificationCompat;

/**
 * Fake notification manager
 */
public class VNotificationManager {
    private static final VNotificationManager sInstance = new VNotificationManager();
    private final NotificationCompat mNotificationCompat;
    private IPCSingleton<INotificationManager> singleton = new IPCSingleton<>(INotificationManager.class);

    private VNotificationManager() {
        mNotificationCompat = NotificationCompat.create();
    }

    public static VNotificationManager get() {
        return sInstance;
    }

    public INotificationManager getService() {
        return singleton.get();
    }

    public boolean dealNotification(int id, Notification notification, String packageName) {
        if (notification == null) return false;
        return VirtualCore.get().getHostPkg().equals(packageName)
                || mNotificationCompat.dealNotification(id, notification, packageName);
    }

    public int dealNotificationId(int id, String packageName, String tag, int userId) {
        try {
            return getService().dealNotificationId(id, packageName, tag, userId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return id;
    }

    public String dealNotificationTag(int id, String packageName, String tag, int userId) {
        try {
            return getService().dealNotificationTag(id, packageName, tag, userId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return tag;
    }

    public boolean areNotificationsEnabledForPackage(String packageName, int userId) {
        try {
            return getService().areNotificationsEnabledForPackage(packageName, userId);
        } catch (RemoteException e) {
            e.printStackTrace();
            return true;
        }
    }

    public void setNotificationsEnabledForPackage(String packageName, boolean enable, int userId) {
        try {
            getService().setNotificationsEnabledForPackage(packageName, enable, userId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void addNotification(int id, String tag, String packageName, int userId) {
        try {
            getService().addNotification(id, tag, packageName, userId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void cancelAllNotification(String packageName, int userId) {
        try {
            getService().cancelAllNotification(packageName, userId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
