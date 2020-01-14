package com.duapps.f.proxydemo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class InterfaceHandler implements InvocationHandler {
    private IInterface target;

    public InterfaceHandler(IInterface _target) {
        target = _target;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if ("getResult".equals(method.getName())) {
            args[0] += "=====after";
            return "before=====" + method.invoke(target, args);
        }
        return null;    }
}
