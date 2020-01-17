package com.duapps.f.lib.client.hook.proxies.mount;

import android.os.IInterface;

import com.duapps.f.lib.client.hook.base.Inject;
import com.duapps.f.lib.client.hook.base.BinderInvocationProxy;
import com.duapps.f.lib.helper.compat.BuildCompat;

import mirror.RefStaticMethod;
import mirror.android.os.mount.IMountService;
import mirror.android.os.storage.IStorageManager;

/**
 * @author Lody
 */
@Inject(MethodProxies.class)
public class MountServiceStub extends BinderInvocationProxy {

    public MountServiceStub() {
        super(getInterfaceMethod(), "mount");
    }

    private static RefStaticMethod<IInterface> getInterfaceMethod() {
        if (BuildCompat.isOreo()) {
            return IStorageManager.Stub.asInterface;
        } else {
            return IMountService.Stub.asInterface;
        }
    }
}
