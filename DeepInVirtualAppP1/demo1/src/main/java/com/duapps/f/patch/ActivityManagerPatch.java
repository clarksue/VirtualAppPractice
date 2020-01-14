package com.duapps.f.patch;

import android.app.ActivityManager;
import android.app.ActivityManagerNative;
import android.app.IActivityManager;
import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ServiceManager;
import android.os.ShellCallback;
import android.util.Singleton;

import com.duapps.f.commonlib.hook.base.HookBinder;
import com.duapps.f.commonlib.hook.base.HookObject;
import com.duapps.f.commonlib.hook.base.Patch;
import com.duapps.f.commonlib.hook.base.PatchObject;
import com.duapps.f.hook.Hook_StartActivity;
import com.duapps.f.hook.Hook_StartActivityAsCaller;
import com.duapps.f.hook.Hook_StartActivityAsUser;

import java.io.FileDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Patch({Hook_StartActivity.class, Hook_StartActivityAsCaller.class, Hook_StartActivityAsUser.class})
public class ActivityManagerPatch extends PatchObject<IActivityManager, HookObject<IActivityManager>> {

    public static IActivityManager getAMN() {
        Method method = null;
        try {
            method = ActivityManagerNative.class.getMethod("getDefault");
            method.setAccessible(true);
            Object o = method.invoke(null);
            return (IActivityManager) o;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
//        return ActivityManagerNative.getDefault();
        return null;
    }

    @Override
    protected HookObject<IActivityManager> initHookObject() {
        return new HookObject<IActivityManager>(getAMN());
    }

    @Override
    public void inject() throws Throwable {
        Field f_gDefault = ActivityManagerNative.class.getDeclaredField("gDefault");
        if (!f_gDefault.isAccessible()) {
            f_gDefault.setAccessible(true);
        }
        if (f_gDefault.getType() == IActivityManager.class) {
            f_gDefault.set(null, getHookObject().getProxyObject());

        } else if (f_gDefault.getType() == Singleton.class) {

            Singleton gDefault = (Singleton) f_gDefault.get(null);
            Field f_mInstance = Singleton.class.getDeclaredField("mInstance");
            if (!f_mInstance.isAccessible()) {
                f_mInstance.setAccessible(true);
            }
            f_mInstance.set(gDefault, getHookObject().getProxyObject());
        } else {
            // 不会经过这里
            throw new UnsupportedOperationException("Singleton is not visible in AMN.");
        }

        HookBinder<IActivityManager> hookAMBinder = new HookBinder<IActivityManager>() {
            @Override
            public synchronized void shellCommand(FileDescriptor in, FileDescriptor out, FileDescriptor err, String[] args, ShellCallback shellCallback, ResultReceiver resultReceiver) throws RemoteException {
                //TODO why?
            }

            @Override
            protected IBinder queryBaseBinder() {
                try {
                    Method m = ServiceManager.class.getDeclaredMethod("getService", String.class);
                    m.setAccessible(true);
                    Object o = m.invoke(null, Context.ACTIVITY_SERVICE);
                    return (IBinder) o;
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

//                return ServiceManager.getService(Context.ACTIVITY_SERVICE);
                return null;
            }

            @Override
            protected IActivityManager createInterface(IBinder baseBinder) {
                return getHookObject().getProxyObject();
            }
        };
        hookAMBinder.injectService(Context.ACTIVITY_SERVICE);
    }

    @Override
    public boolean isEnvBad() {
        return getAMN() != getHookObject().getProxyObject();
    }
}
