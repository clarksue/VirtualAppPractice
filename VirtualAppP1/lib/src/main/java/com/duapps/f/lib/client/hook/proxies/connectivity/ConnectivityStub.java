package com.duapps.f.lib.client.hook.proxies.connectivity;

import android.content.Context;

import com.duapps.f.lib.client.hook.base.BinderInvocationProxy;

import mirror.android.net.IConnectivityManager;

/**
 * @author legency
 */
public class ConnectivityStub extends BinderInvocationProxy {

    public ConnectivityStub() {
        super(IConnectivityManager.Stub.asInterface, Context.CONNECTIVITY_SERVICE);
    }

    @Override
    protected void onBindMethods() {
        super.onBindMethods();
    }
}
