package com.andrestejero.weeklydeals.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.andrestejero.weeklydeals.AppBaseActivity;
import com.andrestejero.weeklydeals.R;
import com.andrestejero.weeklydeals.models.Category;
import com.andrestejero.weeklydeals.models.HomeContainer;
import com.andrestejero.weeklydeals.models.Target;
import com.andrestejero.weeklydeals.utils.StringUtils;
import com.andrestejero.weeklydeals.views.adapters.HomeAdapter;
import com.andrestejero.weeklydeals.views.presenters.HomePresenter;

public class MainActivity extends AppBaseActivity implements
        HomePresenter.HomeView,
        HomeAdapter.OnItemClickListener {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Nullable
    private ViewHolder mViewHolder;

    @Nullable
    private HomePresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        mViewHolder = new ViewHolder();
        mPresenter = new HomePresenter(this, getAppRepository());
        setupRefreshHandler();
        loadHome();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getAppRepository().stopGetHomeContainer();
    }

    private void setupRefreshHandler() {
        if (mViewHolder != null) {
            mViewHolder.refreshControl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    loadHome();
                }
            });
        }
    }

    private void stopRefreshIndicator() {
        if (mViewHolder != null) {
            mViewHolder.refreshControl.setRefreshing(false);
        }
    }

    @Nullable
    @Override
    protected String getSubtitle() {
        return getString(R.string.home_subtitle);
    }

    private void loadHome() {
        if (mPresenter != null) {
            mPresenter.getHome();
        }
    }

    @Override
    public void showLoading() {
        updateVisibilities(View.VISIBLE, View.GONE, View.GONE, View.GONE);
    }

    @Override
    public void showHome(@NonNull HomeContainer homeContainer) {
        stopRefreshIndicator();
        updateVisibilities(View.GONE, View.GONE, View.GONE, View.VISIBLE);
        if (mViewHolder != null) {
            mViewHolder.homeAdapter = new HomeAdapter(this, homeContainer.getBanners(), homeContainer.getCategories());
            mViewHolder.homeAdapter.setOnItemClickListener(this);
            mViewHolder.homeList.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            mViewHolder.homeList.setAdapter(mViewHolder.homeAdapter);
        }
    }

    @Override
    public void showError() {
        stopRefreshIndicator();
        updateVisibilities(View.GONE, View.VISIBLE, View.GONE, View.GONE);
    }

    @Override
    public void onCategoryClick(@NonNull Category category) {
        if (StringUtils.isNotEmpty(category.getId())) {
            Intent intent = new Intent(MainActivity.this, PsnListActivity.class);
            intent.putExtra(PsnListActivity.EXTRA_PSN_LIST_ID, category.getId());
            startActivity(intent);
        }
    }

    @Override
    public void onBannerClick(@NonNull Target target) {
        if (mPresenter != null) {
            mPresenter.openBanner(target);
        }
    }

    @Override
    public void openList(@NonNull String listId) {
        Intent intent = new Intent(MainActivity.this, PsnListActivity.class);
        intent.putExtra(PsnListActivity.EXTRA_PSN_LIST_ID, listId);
        startActivity(intent);
    }

    @Override
    public void openProduct(@NonNull String productId) {
        Intent intent = new Intent(MainActivity.this, ProductDetailActivity.class);
        intent.putExtra(ProductDetailActivity.EXTRA_PRODUCT_ID, productId);
        startActivity(intent);
    }

    // FIXME: 31/3/17 (Andres) unificar esto que se repite
    private void updateVisibilities(int loadingVisibility, int errorVisibility, int emptyVisibility, int contentVisibility) {
        if (mViewHolder != null) {
            mViewHolder.loadingView.setVisibility(loadingVisibility);
            mViewHolder.errorView.setVisibility(errorVisibility);
            mViewHolder.emptyView.setVisibility(emptyVisibility);
            mViewHolder.homeList.setVisibility(contentVisibility);
        }
    }

    private class ViewHolder {
        private SwipeRefreshLayout refreshControl;
        private final View loadingView;
        private final View errorView;
        private final View emptyView;
        private final RecyclerView homeList;
        private HomeAdapter homeAdapter;

        private ViewHolder() {
            refreshControl = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
            loadingView = findViewById(R.id.loadingView);
            errorView = findViewById(R.id.errorView);
            emptyView = findViewById(R.id.emptyView);
            homeList = (RecyclerView) findViewById(R.id.homeList);
        }
    }
}
