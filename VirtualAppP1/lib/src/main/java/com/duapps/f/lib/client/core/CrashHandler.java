package com.duapps.f.lib.client.core;

/**
 * @author Lody
 */

public interface CrashHandler {

    void handleUncaughtException(Thread t, Throwable e);

}
