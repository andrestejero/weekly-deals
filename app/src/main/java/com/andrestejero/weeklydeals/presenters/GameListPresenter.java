package com.andrestejero.weeklydeals.presenters;

import android.support.annotation.NonNull;
import android.util.Log;

import com.andrestejero.weeklydeals.models.Game;
import com.andrestejero.weeklydeals.repositories.AppRepository;

import java.lang.ref.WeakReference;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GameListPresenter {

    private static final String LOG_TAG = GameListPresenter.class.getSimpleName();

    @NonNull
    private final WeakReference<GameListView> weakView;
    @NonNull
    private final AppRepository mAppRepository;

    public GameListPresenter(@NonNull GameListView view, @NonNull AppRepository appRepository) {
        this.weakView = new WeakReference<>(view);
        this.mAppRepository = appRepository;
    }

    public void getGameList() {
        final GameListView view = weakView.get();
        if (view != null) {
            view.showLoading();
            mAppRepository.getGames(new Callback<List<Game>>() {
                @Override
                public void onResponse(Call<List<Game>> call, Response<List<Game>> response) {
                    if (response.isSuccessful()) {
                        view.hideLoading();
                        List<Game> games = response.body();
                        view.showGameList(games);
                    }
                }

                @Override
                public void onFailure(Call<List<Game>> call, Throwable t) {
                    Log.e(LOG_TAG, t.getMessage());
                    view.showErrorGameList();
                }
            });
        }
    }

    public interface GameListView {
        void showLoading();
        void hideLoading();
        void showGameList(@NonNull List<Game> games);
        void showErrorGameList();
    }
}
