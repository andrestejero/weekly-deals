package com.andrestejero.weeklydeals.views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.andrestejero.weeklydeals.AppBaseActivity;
import com.andrestejero.weeklydeals.R;
import com.andrestejero.weeklydeals.views.adapters.GameListAdapter;
import com.andrestejero.weeklydeals.models.Game;
import com.andrestejero.weeklydeals.views.presenters.GameListPresenter;

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
        if (mViewHolder != null) {
            mViewHolder.gameListAdapter.updateGames(games);
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
        private View loadingView;
        private RecyclerView gameListView;
        private final GameListAdapter gameListAdapter;
        private final RecyclerView.LayoutManager mLayoutManager;

        private ViewHolder() {
            loadingView = findViewById(R.id.loadingView);
            gameListView = (RecyclerView) findViewById(R.id.rvGameList);
            gameListAdapter = new GameListAdapter(GameListActivity.this);
            gameListView.setAdapter(gameListAdapter);
            mLayoutManager = new LinearLayoutManager(GameListActivity.this);
            gameListView.setLayoutManager(mLayoutManager);
        }
    }
}
