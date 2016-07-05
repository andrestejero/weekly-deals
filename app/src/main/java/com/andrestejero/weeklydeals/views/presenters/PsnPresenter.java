package com.andrestejero.weeklydeals.views.presenters;

import android.support.annotation.NonNull;
import android.util.Log;

import com.andrestejero.weeklydeals.models.PsnContainer;
import com.andrestejero.weeklydeals.repositories.AppRepository;

import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PsnPresenter {

    private static final String LOG_TAG = PsnPresenter.class.getSimpleName();

    @NonNull
    private final WeakReference<PsnListView> weakView;
    @NonNull
    private final AppRepository mAppRepository;

    public PsnPresenter(@NonNull PsnListView view, @NonNull AppRepository appRepository) {
        this.weakView = new WeakReference<>(view);
        this.mAppRepository = appRepository;
    }

    public void getPsnContainer(@NonNull String id) {
        final PsnListView view = weakView.get();
        if (view != null) {
            view.showLoading();
            mAppRepository.getPsnContainer(id, new Callback<PsnContainer>() {
                @Override
                public void onResponse(Call<PsnContainer> call, Response<PsnContainer> response) {
                    if (response.isSuccessful()) {
                        PsnContainer psnContainer = response.body();
                        view.showPsnContainer(psnContainer);
                    }
                }

                @Override
                public void onFailure(Call<PsnContainer> call, Throwable t) {
                    // TODO borrar log
                    Log.e(LOG_TAG, t.getMessage());
                    view.showErrorGameList();
                }
            });
        }
    }

    public interface PsnListView {
        void showLoading();
        void showPsnContainer(@NonNull PsnContainer psnContainer);
        void showEmptyList();
        void showErrorGameList();
    }
}
