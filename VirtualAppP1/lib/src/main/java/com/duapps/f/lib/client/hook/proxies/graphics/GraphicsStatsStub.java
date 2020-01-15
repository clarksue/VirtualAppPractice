package com.duapps.f.lib.client.hook.proxies.graphics;

import com.duapps.f.lib.client.hook.base.BinderInvocationProxy;
import com.duapps.f.lib.client.hook.base.ReplaceCallingPkgMethodProxy;

import mirror.android.view.IGraphicsStats;


/**
 * @author Lody
 */
public class GraphicsStatsStub extends BinderInvocationProxy {

	public GraphicsStatsStub() {
		super(IGraphicsStats.Stub.asInterface, "graphicsstats");
	}

	@Override
	protected void onBindMethods() {
		super.onBindMethods();
		addMethodProxy(new ReplaceCallingPkgMethodProxy("requestBufferForProcess"));
	}
}
