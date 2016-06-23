package com.andrestejero.weeklydeals.views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.andrestejero.weeklydeals.AppBaseActivity;
import com.andrestejero.weeklydeals.R;
import com.andrestejero.weeklydeals.models.Game;
import com.andrestejero.weeklydeals.presenters.GameListPresenter;
import com.andrestejero.weeklydeals.utils.CollectionUtils;

import java.util.List;

public class GameListActivity extends AppBaseActivity implements GameListPresenter.GameListView {

    private static final String LOG_TAG = GameListActivity.class.getSimpleName();

    @Nullable
    private ViewHolder mViewHolder;

    @Nullable
    private GameListPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_list);
        mViewHolder = new ViewHolder();
        mPresenter = new GameListPresenter(this, getAppRepository());
        loadGameList();
    }

    private void loadGameList() {
        if (mPresenter != null) {
            mPresenter.getGameList();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getAppRepository().stopGetGames();
    }

    @Override
    public void showLoading() {
        updateLoadingView(View.VISIBLE);
    }

    @Override
    public void showGameList(@NonNull List<Game> games) {
        updateLoadingView(View.GONE);
        for (Game game : CollectionUtils.safeIterable(games)) {
            Log.d(LOG_TAG, game.getDescription());
        }
    }

    @Override
    public void showErrorGameList() {
        updateLoadingView(View.GONE);
        Log.d(LOG_TAG, "showErrorGameList");
    }

    private void updateLoadingView(int loadingVisibility) {
        if (mViewHolder != null && mViewHolder.loadingView != null) {
            mViewHolder.loadingView.setVisibility(loadingVisibility);
        }
    }

    private class ViewHolder {
        private final View loadingView;
        private final View errorView;

        private ViewHolder() {
            loadingView = findViewById(R.id.loadingView);
            errorView = findViewById(R.id.errorView);
        }
    }
}
