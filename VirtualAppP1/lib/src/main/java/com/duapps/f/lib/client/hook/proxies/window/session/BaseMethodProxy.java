package com.duapps.f.lib.client.hook.proxies.window.session;

import android.view.WindowManager;

import com.duapps.f.lib.client.hook.base.StaticMethodProxy;
import com.duapps.f.lib.helper.utils.ArrayUtils;

import java.lang.reflect.Method;

/**
 * @author Lody
 */
/*package*/ class BaseMethodProxy extends StaticMethodProxy {

    public BaseMethodProxy(String name) {
        super(name);
    }

    @Override
    public Object call(Object who, Method method, Object... args) throws Throwable {
        int index = ArrayUtils.indexOfFirst(args, WindowManager.LayoutParams.class);
        if (index != -1) {
            WindowManager.LayoutParams attrs = (WindowManager.LayoutParams) args[index];
            if (attrs != null) {
                attrs.packageName = getHostPkg();
            }
        }
        return method.invoke(who, args);
    }
}
