package com.duapps.f.lib.client.stub;

import android.annotation.TargetApi;
import android.app.Service;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.UserHandle;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class StubJob extends Service {
    private static final String TAG = StubJob.class.getSimpleName();
//    private final SparseArray<JobSession> mJobSessions = new SparseArray<>();
    private JobScheduler mScheduler;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public synchronized ComponentName startForegroundServiceAsUser(Intent service, UserHandle user) {
        return null;
    }

}
