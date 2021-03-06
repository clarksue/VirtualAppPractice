package com.duapps.f.lib.client.hook.proxies.bluetooth;

import android.os.Build;

import com.duapps.f.lib.client.hook.base.BinderInvocationProxy;
import com.duapps.f.lib.client.hook.base.StaticMethodProxy;
import com.duapps.f.lib.helper.utils.marks.FakeDeviceMark;

import java.lang.reflect.Method;

import mirror.android.bluetooth.IBluetooth;
import mirror.android.bluetooth.IBluetoothManager;

/**
 * @see android.bluetooth.BluetoothManager
 */
public class BluetoothStub extends BinderInvocationProxy {
    public static final String SERVICE_NAME = Build.VERSION.SDK_INT >= 17 ?
            "bluetooth_manager" :
            "bluetooth";

    public BluetoothStub() {
        super(Build.VERSION.SDK_INT >= 17 ? IBluetoothManager.Stub.asInterface : IBluetooth.Stub.asInterface,
                SERVICE_NAME);
    }

    @Override
    protected void onBindMethods() {
        super.onBindMethods();
        addMethodProxy(new GetAddress());
    }

    @FakeDeviceMark("fake MAC")
    private static class GetAddress extends StaticMethodProxy {

        GetAddress() {
            super("getAddress");
        }

        @Override
        public Object call(Object who, Method method, Object... args) throws Throwable {
            return getDeviceInfo().bluetoothMac;
        }
    }
}
