package com.duapps.f.lib.client.hook.proxies.content;

import com.duapps.f.lib.client.hook.base.BinderInvocationProxy;

import mirror.android.content.IContentService;

/**
 * @author Lody
 *
 * @see IContentService
 */

public class ContentServiceStub extends BinderInvocationProxy {

    public ContentServiceStub() {
        super(IContentService.Stub.asInterface, "content");
    }
}
