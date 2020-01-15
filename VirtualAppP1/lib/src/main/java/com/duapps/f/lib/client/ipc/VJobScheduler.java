package com.duapps.f.lib.client.ipc;

import android.app.job.JobInfo;
import android.os.Parcelable;
import android.os.RemoteException;

import com.duapps.f.lib.client.env.VirtualRuntime;
import com.duapps.f.lib.helper.ipcbus.IPCSingleton;
import com.duapps.f.lib.server.interfaces.IJobService;

import java.util.List;

public class VJobScheduler {

    private static final VJobScheduler sInstance = new VJobScheduler();

    private IPCSingleton<IJobService> singleton = new IPCSingleton<>(IJobService.class);

    public static VJobScheduler get() {
        return sInstance;
    }

    public IJobService getService() {
        return singleton.get();
    }

    public int schedule(JobInfo job) {
        try {
            return getService().schedule(job);
        } catch (RemoteException e) {
            return VirtualRuntime.crash(e);
        }
    }

    public List<JobInfo> getAllPendingJobs() {
        try {
            return getService().getAllPendingJobs();
        } catch (RemoteException e) {
            return VirtualRuntime.crash(e);
        }
    }

    public void cancelAll() {
        try {
            getService().cancelAll();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void cancel(int jobId) {
        try {
            getService().cancel(jobId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    public JobInfo getPendingJob(int jobId) {
        try {
            return getService().getPendingJob(jobId);
        } catch (RemoteException e) {
            return (JobInfo) VirtualRuntime.crash(e);
        }
    }


    public int enqueue(JobInfo job, Object workItem) {
        if (workItem == null) {
            return -1;
        }
        try {
            return getService().enqueue(job, (Parcelable) workItem);
        } catch (RemoteException e) {
            return (Integer) VirtualRuntime.crash(e);
        }
    }
}
