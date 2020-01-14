package com.duapps.f.hook;

import com.duapps.f.patch.ActivityManagerPatch;

public class Hook_StartActivityAsCaller extends Hook_StartActivity {
    /**
     * 这个构造器必须有,用于依赖注入.
     *
     * @param patchObject 注入对象
     */
    public Hook_StartActivityAsCaller(ActivityManagerPatch patchObject) {
        super(patchObject);
    }

    @Override
    public String getName() {
        return "startActivityAsCaller";
    }
}
