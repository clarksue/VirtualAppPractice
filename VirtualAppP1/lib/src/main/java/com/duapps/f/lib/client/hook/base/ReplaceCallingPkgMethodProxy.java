package com.duapps.f.lib.client.hook.base;

import com.duapps.f.lib.client.hook.utils.MethodParameterUtils;

import java.lang.reflect.Method;

/**
 * @author Lody
 */

public class ReplaceCallingPkgMethodProxy extends StaticMethodProxy {

	public ReplaceCallingPkgMethodProxy(String name) {
		super(name);
	}

	@Override
	public boolean beforeCall(Object who, Method method, Object... args) {
		MethodParameterUtils.replaceFirstAppPkg(args);
		return super.beforeCall(who, method, args);
	}
}
