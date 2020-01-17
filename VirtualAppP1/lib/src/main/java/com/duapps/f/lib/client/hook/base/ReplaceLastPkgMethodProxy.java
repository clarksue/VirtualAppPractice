package com.duapps.f.lib.client.hook.base;

import com.duapps.f.lib.client.hook.utils.MethodParameterUtils;

import java.lang.reflect.Method;

/**
 * @author Lody
 */

public class ReplaceLastPkgMethodProxy extends StaticMethodProxy {

	public ReplaceLastPkgMethodProxy(String name) {
		super(name);
	}

	@Override
	public boolean beforeCall(Object who, Method method, Object... args) {
		MethodParameterUtils.replaceLastAppPkg(args);
		return super.beforeCall(who, method, args);
	}
}
