package com.duapps.f.lib.client.hook.base;

import com.duapps.f.lib.client.hook.utils.MethodParameterUtils;

import java.lang.reflect.Method;

/**
 * @author Lody
 */

public class ReplaceSequencePkgMethodProxy extends StaticMethodProxy {

	private int sequence;

	public ReplaceSequencePkgMethodProxy(String name, int sequence) {
		super(name);
		this.sequence = sequence;
	}

	@Override
	public boolean beforeCall(Object who, Method method, Object... args) {
		MethodParameterUtils.replaceSequenceAppPkg(args, sequence);
		return super.beforeCall(who, method, args);
	}
}
