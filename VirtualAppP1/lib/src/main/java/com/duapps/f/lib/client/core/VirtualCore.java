package com.duapps.f.lib.client.core;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.ConditionVariable;
import android.os.IBinder;
import android.os.Looper;
import android.os.Process;
import android.os.RemoteException;

import com.duapps.f.lib.client.env.Constants;
import com.duapps.f.lib.client.env.VirtualRuntime;
import com.duapps.f.lib.client.fixer.ContextFixer;
import com.duapps.f.lib.client.hook.delegate.ComponentDelegate;
import com.duapps.f.lib.client.ipc.ServiceManagerNative;
import com.duapps.f.lib.client.ipc.VActivityManager;
import com.duapps.f.lib.client.ipc.VPackageManager;
import com.duapps.f.lib.client.stub.VASettings;
import com.duapps.f.lib.helper.ipcbus.IPCBus;
import com.duapps.f.lib.helper.ipcbus.IPCSingleton;
import com.duapps.f.lib.helper.ipcbus.IServerCache;
import com.duapps.f.lib.remote.InstalledAppInfo;
import com.duapps.f.lib.server.ServiceCache;
import com.duapps.f.lib.server.interfaces.IAppManager;

import java.util.List;

import mirror.android.app.ActivityThread;

public class VirtualCore {

    @SuppressLint("StaticFieldLeak")
    private static VirtualCore gCore = new VirtualCore();
    private final int myUid = Process.myUid();
    /**
     * Client Package Manager
     */
    private PackageManager unHookPackageManager;
    /**
     * Host package name
     */
    private String hostPkgName;
    /**
     * ActivityThread instance
     */
    private Object mainThread;
    private Context context;
    /**
     * Main ProcessName
     */
    private String mainProcessName;
    /**
     * Real Process Name
     */
    private String processName;
    private ProcessType processType;
    private IPCSingleton<IAppManager> singleton = new IPCSingleton<>(IAppManager.class);
    private boolean isStartUp;
    private PackageInfo hostPkgInfo;
    private int systemPid;
    private ConditionVariable initLock = new ConditionVariable();
    private ComponentDelegate componentDelegate;


    private VirtualCore() {
    }

    public static VirtualCore get() {
        return gCore;
    }

    public static Object mainThread() {
        return get().mainThread;
    }

    public ConditionVariable getInitLock() {
        return initLock;
    }

    public int myUid() {
        return myUid;
    }

    public ComponentDelegate getComponentDelegate() {
        return componentDelegate == null ? ComponentDelegate.EMPTY : componentDelegate;
    }

    public void setComponentDelegate(ComponentDelegate delegate) {
        this.componentDelegate = delegate;
    }

    public synchronized ActivityInfo resolveActivityInfo(Intent intent, int userId) {
        ActivityInfo activityInfo = null;
        if (intent.getComponent() == null) {
            ResolveInfo resolveInfo = VPackageManager.get().resolveIntent(intent, intent.getType(), 0, userId);
            if (resolveInfo != null && resolveInfo.activityInfo != null) {
                activityInfo = resolveInfo.activityInfo;
                intent.setClassName(activityInfo.packageName, activityInfo.name);
            }
        } else {
            activityInfo = resolveActivityInfo(intent.getComponent(), userId);
        }
        if (activityInfo != null) {
            if (activityInfo.targetActivity != null) {
                ComponentName componentName = new ComponentName(activityInfo.packageName, activityInfo.targetActivity);
                activityInfo = VPackageManager.get().getActivityInfo(componentName, 0, userId);
                intent.setComponent(componentName);
            }
        }
        return activityInfo;
    }

    public ActivityInfo resolveActivityInfo(ComponentName componentName, int userId) {
        return VPackageManager.get().getActivityInfo(componentName, 0, userId);
    }

    public int[] getGids() {
        return hostPkgInfo.gids;
    }

    public Context getContext() {
        return context;
    }

    public PackageManager getPackageManager() {
        return context.getPackageManager();
    }

    public String getHostPkg() {
        return hostPkgName;
    }

    public void startup(Context context) throws Throwable {
        if (!isStartUp) {
            if (Looper.myLooper() != Looper.getMainLooper()) {
                throw new IllegalStateException("VirtualCore.startup() must called in main thread.");
            }
            VASettings.STUB_CP_AUTHORITY = context.getPackageName() + "." + VASettings.STUB_DEF_AUTHORITY;
            ServiceManagerNative.SERVICE_CP_AUTH = context.getPackageName() + "." + ServiceManagerNative.SERVICE_DEF_AUTH;
            this.context = context;
            mainThread = ActivityThread.currentActivityThread.call();
            unHookPackageManager = context.getPackageManager();
            hostPkgInfo = unHookPackageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_PROVIDERS);
            IPCBus.initialize(new IServerCache() {
                @Override
                public void join(String serverName, IBinder binder) {
                    ServiceCache.addService(serverName, binder);
                }

                @Override
                public IBinder query(String serverName) {
                    return ServiceManagerNative.getService(serverName);
                }
            });
            detectProcessType();
            InvocationStubManager invocationStubManager = InvocationStubManager.getInstance();
            invocationStubManager.init();
            invocationStubManager.injectAll();
            ContextFixer.fixContext(context);
            isStartUp = true;
            if (initLock != null) {
                initLock.open();
                initLock = null;
            }
        }
    }

    private void detectProcessType() {
        // Host package name
        hostPkgName = context.getApplicationInfo().packageName;
        // Main process name
        mainProcessName = context.getApplicationInfo().processName;
        // Current process name
        processName = ActivityThread.getProcessName.call(mainThread);
        if (processName.equals(mainProcessName)) {
            processType = ProcessType.Main;
        } else if (processName.endsWith(Constants.SERVER_PROCESS_NAME)) {
            processType = ProcessType.Server;
        } else if (VActivityManager.get().isAppProcess(processName)) {
            processType = ProcessType.VAppClient;
        } else {
            processType = ProcessType.CHILD;
        }
        if (isVAppProcess()) {
            systemPid = VActivityManager.get().getSystemPid();
        }
    }

    /**
     * @return If the current process is used to VA.
     */
    public boolean isVAppProcess() {
        return ProcessType.VAppClient == processType;
    }

    /**
     * @return If the current process is the main.
     */
    public boolean isMainProcess() {
        return ProcessType.Main == processType;
    }

    /**
     * @return If the current process is the child.
     */
    public boolean isChildProcess() {
        return ProcessType.CHILD == processType;
    }

    /**
     * @return If the current process is the server.
     */
    public boolean isServerProcess() {
        return ProcessType.Server == processType;
    }

    /**
     * @return the <em>actual</em> process name
     */
    public String getProcessName() {
        return processName;
    }

    /**
     * @return the <em>Main</em> process name
     */
    public String getMainProcessName() {
        return mainProcessName;
    }

    public int getSystemPid() {
        return systemPid;
    }

    /**
     * Process type
     */
    private enum ProcessType {
        /**
         * Server process
         */
        Server,
        /**
         * Virtual app process
         */
        VAppClient,
        /**
         * Main process
         */
        Main,
        /**
         * Child process
         */
        CHILD
    }

    public boolean isAppInstalled(String pkg) {
        try {
            return getService().isAppInstalled(pkg);
        } catch (RemoteException e) {
            return VirtualRuntime.crash(e);
        }
    }

    public int[] getPackageInstalledUsers(String packageName) {
        try {
            return getService().getPackageInstalledUsers(packageName);
        } catch (RemoteException e) {
            return VirtualRuntime.crash(e);
        }
    }

    public boolean isPackageLaunched(int userId, String packageName) {
        try {
            return getService().isPackageLaunched(userId, packageName);
        } catch (RemoteException e) {
            return VirtualRuntime.crash(e);
        }
    }

    private IAppManager getService() {
        return singleton.get();
    }

    public InstalledAppInfo getInstalledAppInfo(String pkg, int flags) {
        try {
            return getService().getInstalledAppInfo(pkg, flags);
        } catch (RemoteException e) {
            return VirtualRuntime.crash(e);
        }
    }

    public List<InstalledAppInfo> getInstalledApps(int flags) {
        try {
            return getService().getInstalledApps(flags);
        } catch (RemoteException e) {
            return VirtualRuntime.crash(e);
        }
    }

    public static PackageManager getPM() {
        return get().getPackageManager();
    }
}
