package com.duapps.f.virtualappp1.delegate;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.os.Build;

import com.duapps.f.lib.client.hook.delegate.TaskDescriptionDelegate;
import com.duapps.f.lib.os.VUserManager;


/**
 * Patch the task description with the (Virtual) user name
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class MyTaskDescriptionDelegate implements TaskDescriptionDelegate {
    @Override
    public ActivityManager.TaskDescription getTaskDescription(ActivityManager.TaskDescription oldTaskDescription) {
        if (oldTaskDescription == null) {
            return null;
        }
        String labelPrefix = "[" + VUserManager.get().getUserName() + "] ";
        String oldLabel = oldTaskDescription.getLabel() != null ? oldTaskDescription.getLabel() : "";

        if (!oldLabel.startsWith(labelPrefix)) {
            // Is it really necessary?
            return new ActivityManager.TaskDescription(labelPrefix + oldTaskDescription.getLabel(), oldTaskDescription.getIcon(), oldTaskDescription.getPrimaryColor());
        } else {
            return oldTaskDescription;
        }
    }
}
