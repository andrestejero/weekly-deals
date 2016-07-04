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
import com.andrestejero.weeklydeals.models.PsnContainer;
import com.andrestejero.weeklydeals.views.adapters.PsnListAdapter;
import com.andrestejero.weeklydeals.views.presenters.PsnPresenter;

public class PsnListActivity extends AppBaseActivity implements
        PsnPresenter.PsnListView,
        PsnListAdapter.OnItemClickListener {

    private static final String LOG_TAG = PsnListActivity.class.getSimpleName();

    @Nullable
    private ViewHolder mViewHolder;

    @Nullable
    private PsnPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.psn_list);

        mViewHolder = new ViewHolder();
        mPresenter = new PsnPresenter(this, getAppRepository());
        mPresenter.getPsnContainer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getAppRepository().stopGetPsnContainer();
    }

    @Override
    public void showLoading() {
        updateLoadingView(View.VISIBLE);
    }

    @Override
    public void showPsnContainer(@NonNull PsnContainer psnContainer) {
        updateLoadingView(View.GONE);
        Log.d(LOG_TAG, psnContainer.getId());
    }

    @Override
    public void showEmptyList() {
        updateLoadingView(View.GONE);
        Log.d(LOG_TAG, "El listado esta vacio");
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

    @Override
    public void onItemClick(int position) {
        /*
        if (CollectionUtils.isNotEmpty(mProducts)) {
            Product product = mProducts.get(position);
            Intent intent = new Intent(GameListActivity.this, GameDetailActivity.class);
            intent.putExtra("GAME_ID", product.getId());
            startActivity(intent);
        }
         */
    }

    private class ViewHolder {
        private View loadingView;
        private RecyclerView psnListView;
        private final PsnListAdapter psnListAdapter;
        private final RecyclerView.LayoutManager mLayoutManager;

        private ViewHolder() {
            loadingView = findViewById(R.id.loadingView);
            psnListView = (RecyclerView) findViewById(R.id.rvPsnList);
            psnListAdapter = new PsnListAdapter(PsnListActivity.this);
            psnListView.setAdapter(psnListAdapter);
            mLayoutManager = new LinearLayoutManager(PsnListActivity.this);
            psnListView.setLayoutManager(mLayoutManager);
            psnListAdapter.setOnItemClickListener(PsnListActivity.this);
        }
    }

}
