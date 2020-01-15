package com.duapps.f.lib.client.core;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Process;

import com.duapps.f.lib.client.env.Constants;
import com.duapps.f.lib.client.ipc.VActivityManager;
import com.duapps.f.lib.client.ipc.VPackageManager;

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
    private int systemPid;


    private VirtualCore() {
    }

    public static VirtualCore get() {
        return gCore;
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
}
