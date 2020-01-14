package com.duapps.f.commonlib.hook.base;

public interface Injectable {
    /**
     * 注入的Impl
     *
     * @throws Throwable
     */
    void inject() throws Throwable;

    /**
     * @return 注入环境是否已经失效.
     */
    boolean isEnvBad();
}
