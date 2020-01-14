package com.duapps.f.commonlib.hook.base;

public interface IHookObject<T> {
    void addHook(Hook hook);

    Hook removeHook(String hookName);

    void removeHook(Hook hook);

    void removeAllHook();

    <H extends Hook> H getHook(String name);

    T getProxyObject();

    T getBaseObject();

    int getHookCount();
}
