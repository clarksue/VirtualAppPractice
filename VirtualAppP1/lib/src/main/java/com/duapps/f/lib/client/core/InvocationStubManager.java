package com.duapps.f.lib.client.core;

import com.duapps.f.lib.client.hook.base.MethodInvocationProxy;
import com.duapps.f.lib.client.hook.base.MethodInvocationStub;
import com.duapps.f.lib.client.hook.delegate.AppInstrumentation;
import com.duapps.f.lib.client.interfaces.IInjector;

import java.util.HashMap;
import java.util.Map;

public final class InvocationStubManager {
    private static InvocationStubManager sInstance = new InvocationStubManager();
    private static boolean sInit;

    private Map<Class<?>, IInjector> mInjectors = new HashMap<>(13);

    private InvocationStubManager() {
    }

    public static InvocationStubManager getInstance() {
        return sInstance;
    }

    void injectAll() throws Throwable {
        for (IInjector injector : mInjectors.values()) {
            injector.inject();
        }
        // XXX: Lazy inject the Instrumentation,
        addInjector(AppInstrumentation.getDefault());
    }

    /**
     * @return if the InvocationStubManager has been initialized.
     */
    public boolean isInit() {
        return sInit;
    }

    public void init() throws Throwable {
        if (isInit()) {
            throw new IllegalStateException("InvocationStubManager Has been initialized.");
        }
        injectInternal();
        sInit = true;

    }

    private void injectInternal() throws Throwable {
        if (VirtualCore.get().isMainProcess()) {
            return;
        }
    }

    private void addInjector(IInjector IInjector) {
        mInjectors.put(IInjector.getClass(), IInjector);
    }

    public <T extends IInjector> T findInjector(Class<T> clazz) {
        // noinspection unchecked
        return (T) mInjectors.get(clazz);
    }

    public <T extends IInjector> void checkEnv(Class<T> clazz) {
        IInjector IInjector = findInjector(clazz);
        if (IInjector != null && IInjector.isEnvBad()) {
            try {
                IInjector.inject();
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    public <T extends IInjector, H extends MethodInvocationStub> H getInvocationStub(Class<T> injectorClass) {
        T injector = findInjector(injectorClass);
        if (injector != null && injector instanceof MethodInvocationProxy) {
            // noinspection unchecked
            return (H) ((MethodInvocationProxy) injector).getInvocationStub();
        }
        return null;
    }
}
