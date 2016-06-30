package com.andrestejero.weeklydeals.views.presenters;

import android.support.annotation.NonNull;
import android.util.Log;

import com.andrestejero.weeklydeals.models.Game;
import com.andrestejero.weeklydeals.models.GameList;
import com.andrestejero.weeklydeals.repositories.AppRepository;
import com.andrestejero.weeklydeals.utils.CollectionUtils;

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
            mAppRepository.getGames(new Callback<GameList>() {
                @Override
                public void onResponse(Call<GameList> call, Response<GameList> response) {
                    if (response.isSuccessful()) {
                        GameList games = response.body();
                        if (CollectionUtils.isNotEmpty(games.getGames())) {
                            view.showGameList(games.getGames());
                        } else {
                            view.showEmptyList();
                        }
                    }
                }

                @Override
                public void onFailure(Call<GameList> call, Throwable t) {
                    Log.e(LOG_TAG, t.getMessage());
                    view.showErrorGameList();
                }
            });
        }
    }

    public interface GameListView {
        void showLoading();
        void showGameList(@NonNull List<Game> games);
        void showEmptyList();
        void showErrorGameList();
    }
}
