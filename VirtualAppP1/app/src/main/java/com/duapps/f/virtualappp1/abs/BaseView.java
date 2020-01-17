package com.duapps.f.virtualappp1.abs;

import android.app.Activity;
import android.content.Context;

public interface BaseView<T> {
    Activity getActivity();
    Context getContext();
    void setPresenter(T presenter);
}
