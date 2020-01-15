package com.duapps.f.lib;

import com.duapps.f.lib.client.core.VirtualCore;

import java.util.Arrays;
import java.util.List;

/**
 * @author Lody
 */
public class GmsSupport {

    private static final List<String> GOOGLE_APP = Arrays.asList(
            "com.android.vending",
            "com.google.android.play.games",
            "com.google.android.wearable.app",
            "com.google.android.wearable.app.cn"
    );

    private static final List<String> GOOGLE_SERVICE = Arrays.asList(
            "com.google.android.gsf",
            "com.google.android.gms",
            "com.google.android.gsf.login",
            "com.google.android.backuptransport",
            "com.google.android.backup",
            "com.google.android.configupdater",
            "com.google.android.syncadapters.contacts",
            "com.google.android.feedback",
            "com.google.android.onetimeinitializer",
            "com.google.android.partnersetup",
            "com.google.android.setupwizard",
            "com.google.android.syncadapters.calendar"
    );

    public static boolean isGmsFamilyPackage(String packageName) {
        return packageName.equals("com.android.vending")
                || packageName.equals("com.google.android.gms");
    }

    public static boolean isGoogleFrameworkInstalled() {
        return VirtualCore.get().isAppInstalled("com.google.android.gms");
    }
}