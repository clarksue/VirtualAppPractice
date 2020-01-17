package com.duapps.f.lib.client.badger;

import android.content.Intent;

import com.duapps.f.lib.remote.BadgerInfo;

/**
 * @author Lody
 */
public interface IBadger {

    String getAction();

    BadgerInfo handleBadger(Intent intent);

}
