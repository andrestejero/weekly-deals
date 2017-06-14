package com.andrestejero.weeklydeals.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;

import com.andrestejero.weeklydeals.AppBaseActivity;
import com.andrestejero.weeklydeals.R;
import com.andrestejero.weeklydeals.models.Category;
import com.andrestejero.weeklydeals.models.HomeContainer;
import com.andrestejero.weeklydeals.models.Target;
import com.andrestejero.weeklydeals.utils.StringUtils;
import com.andrestejero.weeklydeals.views.adapters.HomeAdapter;
import com.andrestejero.weeklydeals.views.presenters.HomePresenter;

import java.util.ArrayList;
import java.util.List;

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
        mViewHolder = new ViewHolder();
        mPresenter = new HomePresenter(this, getAppRepository());
        setupRefreshHandler();
        loadHome();
        setupSearchView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mViewHolder != null) {
            mViewHolder.searchView.clearFocus();
        }
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
            mViewHolder.homeAdapter = new HomeAdapter(this, homeContainer.getBanners(), getCategoryHome(homeContainer));
            mViewHolder.homeAdapter.setOnItemClickListener(this);
            mViewHolder.homeList.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            mViewHolder.homeList.setAdapter(mViewHolder.homeAdapter);
        }
    }

    private List<Category> getCategoryHome(@NonNull HomeContainer homeContainer) {
        List<Category> categories = new ArrayList<>();
        for (Category category : homeContainer.getCategories()) {
            for (Category childCategory : category.getCategories()) {
                categories.add(childCategory);
            }
        }
        return categories;
    }

    @Override
    public void showError() {
        stopRefreshIndicator();
        updateVisibilities(View.GONE, View.VISIBLE, View.GONE, View.GONE);
    }

    @Override
    public void onCategoryClick(@NonNull Category category) {
        if (StringUtils.isNotEmpty(category.getId())) {
            Intent intent = PsnListActivity.newIntent(this, category.getId());
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
        Intent intent = PsnListActivity.newIntent(this, listId);
        startActivity(intent);
    }

    @Override
    public void openProduct(@NonNull String productId) {
        Intent intent = ProductDetailActivity.newIntent(this, productId);
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

    private void setupSearchView() {
        if (mViewHolder != null) {
            mViewHolder.searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        startSearchActivity();
                    }
                }
            });
            mViewHolder.searchView.setQueryHint(getString(R.string.search));
            mViewHolder.searchView.setFocusable(true);
            mViewHolder.searchView.setIconifiedByDefault(false);
        }
    }

    private void startSearchActivity() {
        if (mViewHolder != null) {
            mViewHolder.searchView.clearFocus();
        }
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    private class ViewHolder {
        private SwipeRefreshLayout refreshControl;
        private final View loadingView;
        private final View errorView;
        private final View emptyView;
        private final RecyclerView homeList;
        private HomeAdapter homeAdapter;
        private final SearchView searchView;

        private ViewHolder() {
            refreshControl = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
            loadingView = findViewById(R.id.loadingView);
            errorView = findViewById(R.id.errorView);
            emptyView = findViewById(R.id.emptyView);
            homeList = (RecyclerView) findViewById(R.id.homeList);
            searchView = (SearchView) findViewById(R.id.searchView);
        }
    }
}
