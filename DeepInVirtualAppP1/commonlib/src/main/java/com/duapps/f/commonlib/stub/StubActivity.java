package com.duapps.f.commonlib.stub;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.UserHandle;

public abstract class StubActivity extends Activity {
    public static class Stub1 extends StubActivity {
        @Override
        public synchronized ComponentName startForegroundServiceAsUser(Intent service, UserHandle user) {
            return null;
        }//注意此处必须是静态的,否则会出现错误
    }
}
