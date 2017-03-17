package com.andrestejero.weeklydeals.views.presenters;

import android.support.annotation.NonNull;

import com.andrestejero.weeklydeals.models.HomeContainer;
import com.andrestejero.weeklydeals.repositories.AppRepository;

import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePresenter {

    private static final String LOG_TAG = HomePresenter.class.getSimpleName();

    @NonNull
    private final WeakReference<HomeView> weakView;
    @NonNull
    private final AppRepository mAppRepository;

    public HomePresenter(@NonNull HomeView view, @NonNull AppRepository mAppRepository) {
        this.weakView = new WeakReference<>(view);
        this.mAppRepository = mAppRepository;
    }

    public void getHome() {
        HomeView view = weakView.get();
        if (view != null) {
            view.showLoading();
            mAppRepository.getHomeContainer(new Callback<HomeContainer>() {
                @Override
                public void onResponse(Call<HomeContainer> call, Response<HomeContainer> response) {
                    HomeView view = weakView.get();
                    if (view != null) {
                        view.showHome(response.body());
                    }
                }

                @Override
                public void onFailure(Call<HomeContainer> call, Throwable t) {
                    HomeView view = weakView.get();
                    if (view != null) {
                        view.showError();
                    }
                }
            });

        }
    }

    public interface HomeView {
        void showLoading();

        void showHome(@NonNull HomeContainer homeContainer);

        void showError();
    }
}
