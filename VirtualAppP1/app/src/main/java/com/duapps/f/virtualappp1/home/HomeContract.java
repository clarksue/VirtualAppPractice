package com.duapps.f.virtualappp1.home;


import com.duapps.f.virtualappp1.abs.BasePresenter;
import com.duapps.f.virtualappp1.abs.BaseView;
import com.duapps.f.virtualappp1.home.models.AppData;
import com.duapps.f.virtualappp1.home.models.AppInfoLite;

import java.util.List;

/**
 * @author Lody
 */
/* package */ class HomeContract {

	/* package */ interface HomeView extends BaseView<HomePresenter> {

        void showBottomAction();

        void hideBottomAction();

		void showLoading();

		void hideLoading();

		void loadFinish(List<AppData> appModels);

		void loadError(Throwable err);

		void showGuide();

		void addAppToLauncher(AppData model);

        void removeAppToLauncher(AppData model);

		void refreshLauncherItem(AppData model);

		void askInstallGms();
	}

	/* package */ interface HomePresenter extends BasePresenter {

		void launchApp(AppData data);

		void dataChanged();

		void addApp(AppInfoLite info);

		void deleteApp(AppData data);

        void createShortcut(AppData data);
    }

}
