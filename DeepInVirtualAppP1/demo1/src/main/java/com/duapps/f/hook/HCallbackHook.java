package com.duapps.f.hook;

import android.app.ActivityThread;
import android.os.Handler;
import android.os.Message;

import com.duapps.f.commonlib.hook.base.Injectable;

import java.lang.reflect.Field;

public class HCallbackHook implements Handler.Callback, Injectable {

    public static final int LAUNCH_ACTIVITY = 100;

    private static final String TAG = HCallbackHook.class.getSimpleName();
    private static final HCallbackHook sCallback = new HCallbackHook();
    private static Field f_h;
    private static Field f_handleCallback;

    static {
        try {
            f_h = ActivityThread.class.getDeclaredField("mH");
            f_handleCallback = Handler.class.getDeclaredField("mCallback");
            f_h.setAccessible(true);
            f_handleCallback.setAccessible(true);
        } catch (NoSuchFieldException e) {
            // Ignore
        }
    }

    /**
     * 其它插件化可能也会注入Activity$H, 这里要保留其它插件化的Callback引用，我们的Callback完事后再调用它的。
     */
    private Handler.Callback otherCallback;

    private HCallbackHook() {
    }

    public static HCallbackHook getDefault() {
        return sCallback;
    }

    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }

    @Override
    public void inject() throws Throwable {

    }

    @Override
    public boolean isEnvBad() {
        return false;
    }
}
