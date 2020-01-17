package com.duapps.f.virtualappp1.home;

import com.duapps.f.virtualappp1.abs.BasePresenter;
import com.duapps.f.virtualappp1.abs.BaseView;
import com.duapps.f.virtualappp1.home.models.AppInfo;

import java.util.List;

/**
 * @author Lody
 * @version 1.0
 */
/*package*/ class ListAppContract {
    interface ListAppView extends BaseView<ListAppPresenter> {

        void startLoading();

        void loadFinish(List<AppInfo> infoList);
    }

    interface ListAppPresenter extends BasePresenter {

    }
}
