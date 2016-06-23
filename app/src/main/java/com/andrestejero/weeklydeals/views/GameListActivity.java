package com.andrestejero.weeklydeals.views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.andrestejero.weeklydeals.AppBaseActivity;
import com.andrestejero.weeklydeals.R;
import com.andrestejero.weeklydeals.adapters.GameListAdapter;
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
        if (mViewHolder != null) {
            mViewHolder.loadingView.setVisibility(loadingVisibility);
        }
    }

    private class ViewHolder {
        @NonNull
        private final View loadingView;

        @NonNull
        final RecyclerView gameListView;

        @NonNull
        final GameListAdapter gameListAdapter;

        private ViewHolder() {

            View v = findViewById(R.id.loadingView);
            assert v != null;
            loadingView = v;

            RecyclerView rv = (RecyclerView) findViewById(R.id.rvGameList);
            assert rv != null;
            gameListView = rv;

            gameListAdapter = new GameListAdapter(GameListActivity.this);
            gameListView.setAdapter(gameListAdapter);
        }
    }
}
