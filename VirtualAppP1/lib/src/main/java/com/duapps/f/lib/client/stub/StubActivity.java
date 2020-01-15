package com.duapps.f.lib.client.stub;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.duapps.f.lib.client.VClientImpl;
import com.duapps.f.lib.client.core.InvocationStubManager;
import com.duapps.f.lib.client.env.VirtualRuntime;
import com.duapps.f.lib.client.hook.proxies.am.HCallbackStub;
import com.duapps.f.lib.client.ipc.VActivityManager;
import com.duapps.f.lib.os.VUserHandle;
import com.duapps.f.lib.remote.StubActivityRecord;

public abstract class StubActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // The savedInstanceState's classLoader is not exist.
        super.onCreate(null);
        finish();
        // It seems that we have conflict with the other Android-Plugin-Framework.
        Intent stubIntent = getIntent();
        // Try to acquire the actually component information.
        StubActivityRecord r = new StubActivityRecord(stubIntent);
        if (r.intent != null) {
            if (TextUtils.equals(r.info.processName, VirtualRuntime.getProcessName()) && r.userId == VUserHandle.myUserId()) {
                // Retry to inject the HCallback to instead of the exist one.
                InvocationStubManager.getInstance().checkEnv(HCallbackStub.class);
                Intent intent = r.intent;
                intent.setExtrasClassLoader(VClientImpl.get().getCurrentApplication().getClassLoader());
                startActivity(intent);
            } else {
                // Start the target Activity in other process.
                VActivityManager.get().startActivity(r.intent, r.userId);
            }
        }
    }
}
