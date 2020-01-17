// IBinderDelegateService.aidl
package com.duapps.f.lib.server;

import android.content.ComponentName;

interface IBinderDelegateService {

   ComponentName getComponent();

   IBinder getService();

}
